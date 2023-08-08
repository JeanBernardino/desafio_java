import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Main {
    public static void main(String[] args) {
        //Path do arquivo json
        String jsonFilePath = System.getProperty("user.dir") + "/src/data.json";
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(jsonFilePath));
            atualizarEstoque(jsonArray);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void atualizarEstoque(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            System.out.println("O array passado está vazio!");
            return;
        }

        try {
            // Iniciar a conexão com o banco de dados
            Connection connection = Database.getConnection();

            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj = (JSONObject) iterator.next();

                //Atributos do dicionário
                String tamanho = obj.get("tamanho").toString();
                String produto = obj.get("produto").toString();
                String cor = obj.get("cor").toString();
                String deposito = obj.get("deposito").toString();
                String dataDisponibilidade = obj.get("data_disponibilidade").toString();
                int quantidade = Integer.parseInt(obj.get("quantidade").toString());

                String sql = "INSERT INTO estoque(tamanho, produto, cor, deposito, data_disponibilidade, quantidade) VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE tamanho = VALUES(tamanho), produto = VALUES(produto), cor = VALUES(cor), deposito = VALUES(deposito), "+
                        "data_disponibilidade = VALUES(data_disponibilidade), quantidade = quantidade + VALUES(quantidade)";

                // Setar atributos a query SQL
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, tamanho);
                statement.setString(2, produto);
                statement.setString(3, cor);
                statement.setString(4, deposito);
                statement.setString(5, dataDisponibilidade);
                statement.setInt(6, quantidade);
                statement.executeUpdate();
            }

            //Fechar conexão com o banco
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
