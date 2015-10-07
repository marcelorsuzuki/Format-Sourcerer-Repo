package br.unifesp.repo2sourcerer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.unifesp.repo2sourcerer.file.ManageDir;

public class MainClass {

	public static void main(String[] args) {
		
		String inputDir = "/home/repository/Mestrado/repo/pre-repo";
		String outputDir = "/home/repository/Mestrado/repo/test-repo";
		
		try {
			
			String lastDir = File.separator + (ManageDir.getLastDirSourcerer(outputDir) + 1);

			List<String> dirs = ManageDir.listAllDirectories(inputDir);
			int prj = 1;
			for (String d : dirs) {
				ManageDir.copyIntoRepo(d, outputDir + lastDir + File.separator + prj);
				
				System.out.println("Projeto " + prj);
				
				prj++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
