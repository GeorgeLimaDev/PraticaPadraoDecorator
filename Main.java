public class Main {
    public static void main(String[] args) {
        String registrosSalario = "Nome,Salário\nJoão Silva,100000\nMaria Oliveira,110000";
        String informacoesPessoais = "Nome,CPF,Idade\nCarlos Souza,12345678900,30\nAna Pereira,98765432100,25";

        DataSource fonteCodificada = new CompressionDecorator(
                new EncryptionDecorator(
                        new FileDataSource("ArquivoCodificado.txt")));

        fonteCodificada.writeData(registrosSalario);
        System.out.println("Dados de salário criptografados e comprimidos gravados no arquivo.");

        System.out.println("Dados criptografados e comprimidos do arquivo:");
        System.out.println(fonteCodificada.readData());

        fonteCodificada.writeData(informacoesPessoais);
        System.out.println("\nInformações pessoais criptografadas e comprimidas gravadas no arquivo.");
        System.out.println("Dados criptografados e comprimidos do arquivo:");
        System.out.println(fonteCodificada.readData());

        DataSource fonteOriginal = new FileDataSource("ArquivoCodificado.txt");
        System.out.println("\nDados brutos do arquivo (sem descompressão ou descriptografia):");
        System.out.println(fonteOriginal.readData());

        System.out.println("\nDados descriptografados e descomprimidos do arquivo:");
        System.out.println(fonteCodificada.readData());

        DataSource fonteApenasCriptografada = new EncryptionDecorator(
                new FileDataSource("ArquivoApenasCriptografado.txt"));
        fonteApenasCriptografada.writeData("Texto secreto confidencial");
        System.out.println("\nDados apenas criptografados gravados no arquivo.");
        System.out.println("Dados criptografados do arquivo:");
        System.out.println(fonteApenasCriptografada.readData());

        DataSource fonteApenasComprimida = new CompressionDecorator(
                new FileDataSource("ArquivoApenasComprimido.txt"));
        fonteApenasComprimida.writeData("Texto que precisa ser comprimido");
        System.out.println("\nDados apenas comprimidos gravados no arquivo.");
        System.out.println("Dados comprimidos do arquivo:");
        System.out.println(fonteApenasComprimida.readData());
    }
}
