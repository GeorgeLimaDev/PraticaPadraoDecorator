import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionDecorator extends DataSourceDecorator {

    public CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(compress(data));
    }

    @Override
    public String readData() {
        return decompress(super.readData());
    }

    private String compress(String data) {
        byte[] input = data.getBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            bos.write(buffer, 0, count);
        }
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    private String decompress(String data) {
        byte[] input = Base64.getDecoder().decode(data);
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                bos.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bos.toByteArray());
    }
}
