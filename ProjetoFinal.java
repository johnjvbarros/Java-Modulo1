import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjetoFinal {
    // Integrantes do Grupo:
    // Andre Camargo
    // Enzo Ramos
    // Pietro Zardini
    // Joao Victor

    /*
    Crie um programa de uma loja com as seguintes opções abaixo, para cada operação,
    deve ser manipulado arquivo para salvar as informações dos produtos, para que caso saia do programa as informações persistam.

Obs: só vai precisar salvar do arquivo as informações dos produtos.


* Compra de produtos, onde o usuário pode escolher produtos e quantidades conforme ele queira,
 assim que escolher finalizar, mostre tudo que ele comprou, os preços e o total.
  Quando ele for escolher o produto e quantidade, faça uma verificação se o produto tem aquela
   quantidade, caso não tenha, informe ao usuário que não contém a quantidade deste produto no
   estoque. Assim que o usuário confirmar a compra, deduza as quantidades dos produtos selecionados.


     */
    static Scanner input = new Scanner(System.in);
    static List<String> listaProdutos = new ArrayList<>();
    static Path path = Paths.get("C:\\Users\\joaojvbarros\\Java\\cart.txt");

    public static void main(String[] args) throws IOException {

        boolean continuar=true;
        String opcaoEntrada;

        while(continuar){
            System.out.println("Digite a opção correspondente à sua escolha.");
            //Front
            System.out.println("1 - Criar produto.");
            System.out.println("2 - Editar produto.");
            System.out.println("3 - Excluir produto.");
            //End
            System.out.println("4 - Pesquisar produto.");
            System.out.println("5 - Comprar produto.");
            System.out.println("0 - Encerrar");

            opcaoEntrada = input.nextLine();
            switch (opcaoEntrada){
                case "1"-> criarProduto();
                case "2"-> editarProduto();
                case "3"-> excluirProduto();
                case "4"-> pesquisarProduto();
                case "5"-> comprarProduto();
                case "0"->{
                    System.out.println("Saindo!");
                    continuar=false;
                }
                default-> System.out.println("Opção inválida");
            }
        }

    }

    private static void comprarProduto() throws IOException {

        System.out.println("Selecione o ID do produto desejado: ");
        String id = input.nextLine();
        System.out.println("Quantidade desejada: ");
        Integer quantidadeCarrinho = Integer.valueOf(input.nextLine());

        List<String> listaProdutos =  Files.readAllLines(path);
        List<String> carrinho = new ArrayList<>();
        for(String string: listaProdutos){
            String idCarrinho = string.split("\\|")[0];
            Integer quantidadeEstoque = Integer.valueOf(string.split("\\|")[3]);
            if(idCarrinho.equals(id) && quantidadeCarrinho <= quantidadeEstoque){
                carrinho.add(string);
            }
        }

    }

    private static void editarProduto() throws IOException {
        List<String> listaProdutos = listarProduto();
        System.out.println("Selecione o item (ID) para editar: ");
        Integer idEditar = Integer.valueOf(input.nextLine()) - 1;

        System.out.println("Digite o novo nome do produto");
        String produto = input.nextLine();

        System.out.printf("Digite o novo preco de %s: ", produto);
        Double preco = Double.valueOf(input.nextLine());

        System.out.println("Digite a nova quantidade: ");
        Integer quantidade = Integer.valueOf(input.nextLine());
        Integer id = idEditar + 1;
        listaProdutos.set(idEditar, id+"|"+produto+"|"+preco+"|"+quantidade);

        String novoProduto = "";
        for (String s: listaProdutos){
            novoProduto += s +"\n";
        }
        Files.writeString(
                path, novoProduto );
        System.out.println("Produto alterado com sucesso!");
    }

    private static void excluirProduto() throws IOException {
        List<String> listaProdutos = listarProduto();
        System.out.println("Selecione o item (ID) para remover");
        Integer idEditar = Integer.valueOf(input.nextLine()) - 1;

        // remove a linha
        listaProdutos.remove(listaProdutos.get(idEditar));

        String novoProduto = "";
        for (String s: listaProdutos){
            novoProduto += s +"\n";
        }

        Files.writeString(path, novoProduto );
        System.out.println("Produto exluido com sucesso!");
    }

    private static void pesquisarProduto()throws IOException{

        List<String> listaStrings = Files.readAllLines(path);

        System.out.println("Digite o nome do produto desejado:");
        String nomeProduto = input.nextLine();

        List<String> resultadoBusca = new ArrayList<>();
        for(String string: listaStrings){
            if(string.contains(nomeProduto)){
                resultadoBusca.add(string);
            }
        }
        if(resultadoBusca.size()==0){
            System.out.println("Não encontrou nenhum produto!");
            System.out.println("\n\n");
        }

        String produto, quantidade, preco, id;

        for(String string: resultadoBusca){
            // tira as strings e cria as variaveis
            id = string.split("\\|")[0];
            produto = string.split("\\|")[1];
            preco = string.split("\\|")[2];
            quantidade = string.split("\\|")[3];

            System.out.printf("ID: %s | Produto: %s | Preço: R$ %s | quantidade: %s\n", id, produto, preco, quantidade);
        }
    }
    private static List<String> listarProduto()throws IOException {
        List<String> listaStrings = Files.readAllLines(path);
        String produto, quantidade, preco, id;

        for(String string: listaStrings){
            // tira as strings e cria as variaveis
            id = string.split("\\|")[0];
            produto = string.split("\\|")[1];
            preco = string.split("\\|")[2];
            quantidade = string.split("\\|")[3];

            System.out.printf("ID: %s | Produto: %s | Preço: R$ %s | quantidade: %s\n", id, produto, preco, quantidade);
        }
        return listaStrings;
    }

    public static void criarProduto() throws IOException {
        System.out.println("Digite o nome do produto");
        String produto = input.nextLine();

        System.out.printf("Digite o preco de %s: ", produto);
        Double preco = Double.valueOf(input.nextLine());

        System.out.println("Digite a quantidade desejada: ");
        Integer quantidade = Integer.valueOf(input.nextLine());

        if(!Files.exists(path)){
            Files.createFile(path);
        }
        List<String> listaStrings = Files.readAllLines(path);
        String parametros = listaStrings.get(listaStrings.size()-1).split("\\|")[0];
        Integer id = Integer.valueOf(parametros) + 1;
        Files.writeString(path, id +"|"+ produto+"|"+preco+"|"+quantidade+" \n", StandardOpenOption.APPEND);

    }
}
