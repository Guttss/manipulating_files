/* Antes de tudo crie um arquivo com o nome de "input.csv"
 * e adiciona os valores dentro dele, como nome, preco e quantidade,
 * e no console passe o caminho para este arquivo
 * Exemplo: C:\Windows\Temp\input.csv
 */

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Products;

public class Program {

	public static void main(String[] args) throws ParseException{
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Products> list = new ArrayList<>();
		
		System.out.print("Enter file path: ");
		String sourceFileStr = sc.nextLine();
		
		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent(); //pega somente o caminho ignorando o arquivo
		
		//retorna se foi possivel criar ou nao uma pasta
		boolean sucess = new File(sourceFolderStr + "\\out").mkdirs();
		
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		//try-with-resource
		//FileReader permiti um leitura de caracteres a partir de um arquivo
		//BufferedReader instanciado apartir de um FR, sendo mais rapido pois usa um buffered de memoria
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
			
			String itemCsv = br.readLine();
			while(itemCsv != null) {
				
				String[] fields = itemCsv.split(",");
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);
				
				list.add(new Products(name, price, quantity));
				
				itemCsv = br.readLine();
			}
			
			//FileWriter e BufferedWriter tem a mesma funcao, so que para a gravacao de arquivos
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
				
				for (Products item: list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " CREATED");
			}
			catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}
		}
		catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
		
		sc.close();
	}

}
