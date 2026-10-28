package saep;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ItenDAO itemDAO = new ItenDAO();

    public static void main(String[] args) {
        System.out.println("=== Sistema de Almoxarifado ===");

        while (true) {
            if (CadastroUsuario.getUsuarioLogado() == null) {
                exibirMenuNaoLogado();
            } else {
                exibirMenuLogado();
            }

            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
    }

    private static void exibirMenuNaoLogado() {
        System.out.println();
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Entrar no sistema");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirMenuLogado() {
        Usuario usuario = CadastroUsuario.getUsuarioLogado();
        System.out.println();
        System.out.println("Usuário logado: " + usuario.getNome() + " (" + usuario.getPerfil().getDescricao() + ")");
        System.out.println("1 - Cadastrar item");
        System.out.println("2 - Listar itens");
        System.out.println("3 - Registrar movimentação");
        System.out.println("4 - Listar movimentações");
        System.out.println("5 - Sair da sessão");
        System.out.println("0 - Encerrar sistema");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        while (!scanner.hasNextInt()) {
            System.out.print("Opção inválida. Digite um número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static void processarOpcao(int opcao) {
        if (CadastroUsuario.getUsuarioLogado() == null) {
            switch (opcao) {
                case 1 -> processarCadastroUsuario();
                case 2 -> processarLogin();
                case 0 -> finalizarSistema();
                default -> System.out.println("Opção inválida.");
            }
            return;
        }

        switch (opcao) {
            case 1 -> processarCadastroItem();
            case 2 -> listarItens();
            case 3 -> processarMovimentacao();
            case 4 -> listarMovimentacoes();
            case 5 -> sairDaSessao();
            case 0 -> finalizarSistema();
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void processarCadastroUsuario() {
        if (CadastroUsuario.getUsuarioLogado() != null) {
            System.out.println("Você já está logado no sistema.");
            return;
        }

        String nome = lerTexto("Nome: ");
        String login = lerTexto("Login: ");
        String senha = lerTexto("Senha: ");

        System.out.println("Escolha o perfil:");
        System.out.println("1 - Operador de almoxarifado");
        System.out.println("2 - Administrador do sistema");
        System.out.print("Perfil: ");
        int perfilOpcao = lerOpcao();

        PerfilUsuario perfil = perfilOpcao == 2
                ? PerfilUsuario.ADMINISTRADOR_SISTEMA
                : PerfilUsuario.OPERADOR_ALMOXARIFADO;

        Usuario novoUsuario = new Usuario(nome, login, senha, perfil);
        if (CadastroUsuario.cadastrarUsuario(novoUsuario)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Login já existente. Tente outro.");
        }
    }

    private static void processarLogin() {
        if (CadastroUsuario.getUsuarioLogado() != null) {
            System.out.println("Você já está autenticado.");
            return;
        }

        String login = lerTexto("Login: ");
        String senha = lerTexto("Senha: ");

        if (CadastroUsuario.autenticar(login, senha)) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Login ou senha inválidos.");
        }
    }

    private static void processarCadastroItem() {
        if (CadastroUsuario.getUsuarioLogado() == null) {
            System.out.println("É necessário autenticar antes de cadastrar item.");
            return;
        }

        String nome = lerTexto("Nome do item: ");
        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();
        String descricao = lerTexto("Descrição: ");

        Almoxarifado item = new Almoxarifado(nome, quantidade, descricao);
        itemDAO.cadastrar(item);
        System.out.println("Item cadastrado com sucesso! ID: " + item.getId());
    }

    private static void listarItens() {
        List<Almoxarifado> itens = itemDAO.consultar();
        if (itens.isEmpty()) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }

        System.out.println("Itens cadastrados:");
        for (Almoxarifado item : itens) {
            System.out.println("- ID " + item.getId() + " | " + item.getNome() + " | Quantidade: " + item.getQuantidade() + " | " + item.getDescricao());
        }
    }

    private static void processarMovimentacao() {
        if (CadastroUsuario.getUsuarioLogado() == null) {
            System.out.println("É necessário autenticar antes de registrar movimentação.");
            return;
        }

        listarItens();
        if (itemDAO.consultar().isEmpty()) {
            return;
        }

        System.out.print("Informe o ID do item: ");
        int itemId = lerOpcao();
        System.out.println("Tipo da movimentação:");
        System.out.println("1 - Entrada");
        System.out.println("2 - Saída");
        System.out.print("Escolha: ");
        int tipoOpcao = lerOpcao();
        TipoMovimentacao tipo = tipoOpcao == 1 ? TipoMovimentacao.ENTRADA : TipoMovimentacao.SAIDA;

        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();
        String descricao = lerTexto("Descrição da movimentação: ");

        try {
            itemDAO.registrarMovimentacao(itemId, tipo, quantidade, CadastroUsuario.getUsuarioLogado(), descricao);
            System.out.println("Movimentação registrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarMovimentacoes() {
        List<MovimentacaoEstoque> movimentacoes = itemDAO.consultarMovimentacoes();
        if (movimentacoes.isEmpty()) {
            System.out.println("Nenhuma movimentação registrada.");
            return;
        }

        System.out.println("Movimentações:");
        for (MovimentacaoEstoque movimentacao : movimentacoes) {
            System.out.println("- " + movimentacao.getDataHora() + " | Item: " + movimentacao.getItemId()
                    + " | " + movimentacao.getTipo() + " | Qtd: " + movimentacao.getQuantidade()
                    + " | Responsável: " + movimentacao.getUsuarioResponsavel().getNome()
                    + " | " + movimentacao.getDescricao());
        }
    }

    private static void sairDaSessao() {
        CadastroUsuario.sairSistema();
        System.out.println("Sessão encerrada.");
    }

    private static void finalizarSistema() {
        System.out.println("Sistema encerrado.");
        System.exit(0);
    }
}