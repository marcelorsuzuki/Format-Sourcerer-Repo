package br.unifesp.repo2sourcerer.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 
 * Classe utilizada para manipular diretórios, conforme o projeto Sourcerer trabalha
 * 
 * @author Marcelo Suzuki
 *
 */
public class ManageDir {

	/**
	 * Lista todos os subdiretórios de projeto de um diretório
	 * 
	 * @param path Nome do diretório que contém os projetos
	 * 
	 * @return Lista dos nomes dos diretórios dos projetos
	 * 
	 * @throws IOException
	 */
	public static List<String> listAllDirectories(String path) throws IOException {
		
		List<String> result = new ArrayList<>();

		Path basePath = Paths.get(path);
		DirectoryStream<Path> pathList = Files.newDirectoryStream(basePath);
		for (Path p : pathList) {
			
			if (Files.isDirectory(p)) {
				result.add(p.toString());
			}
		}

		return result;
	}
	
	
	/**
	 * Lista todos os arquivos de um diretório
	 * 
	 * @param path Nome do diretório que contém os arquivos
	 * 
	 * @return Lista dos nomes dos arquivos do diretório 
	 * 
	 * @throws IOException
	 */
	public static List<String> listAllFiles(String path) throws IOException {
		
		List<String> result = new ArrayList<>();

		Path basePath = Paths.get(path);
		DirectoryStream<Path> pathList = Files.newDirectoryStream(basePath);
		for (Path p : pathList) {
			
			if (!Files.isDirectory(p)) {
				result.add(p.toString());
			}
		}

		return result;
	}

	
	/**
	 * Obtém o último diretório de projetos inseridos no Sourcerer.
	 * 
	 * @param path Caminho onde estão os projetos do Sourcerer
	 * 
	 * @return Número do último diretório de projetos do Sourcerer inseridos
	 * 
	 * @throws IOException
	 */
	public static int getLastDirSourcerer(String path) throws IOException {
		
		List<String> dirs = listAllDirectories(path);
		int maior = 0;
		
		for (String d : dirs) {
			
			int idx = d.lastIndexOf(File.separator);
			String name = d.substring(idx + 1);
			
			if (name.matches("\\d+")) {
				if (Integer.parseInt(name) > maior) {
					maior = Integer.parseInt(name);
				}
			}
		}
		
		return maior;
	}
	
	public static void copyIntoRepo(String source, String destination) throws IOException {
		
		
		List<String> dirs = listAllDirectories(source);
		for (String d : dirs) {
			if (!d.toString().equalsIgnoreCase(source + File.separator + "evosuite-files") && 
				!d.toString().equalsIgnoreCase(source + File.separator + "evosuite-tests") &&
				!d.toString().equalsIgnoreCase(source + File.separator + "test-lib")) {

				//Diretório a ser copiado
				File srcDir = new File(d);
				
				//Diretório onde será copiado
				int idx = d.lastIndexOf(File.separator);
				File destDir = new File(destination + File.separator + "content" + File.separator + d.substring(idx + 1));
				
				FileUtils.copyDirectory(srcDir, destDir);
			}
		}

		
		List<String> files = listAllFiles(source);
		for (String f : files) {
			if (!f.toString().equalsIgnoreCase(source + File.separator + "DETAILS.txt")) {

				//Diretório a ser copiado
				File srcFile = new File(f);
				
				//Diretório onde será copiado
				int idx = f.lastIndexOf(File.separator);
				File destFile= new File(destination + File.separator + "content" + File.separator + f.substring(idx + 1));
				
				FileUtils.copyFile(srcFile, destFile);
			}
		}
		
		createProperties(source, destination);
		
	}
	
	private static void createProperties(String source, String destination) throws IOException {
		
		Path path = Paths.get(destination + File.separator + "project.properties");
		int idx = source.lastIndexOf("_");
		
		String msg = "name=" + source.substring(idx + 1);
        Files.write(path, msg.getBytes());
        
	}
	
}
