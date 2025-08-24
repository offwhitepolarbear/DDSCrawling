package com.kihwangkwon.businesslogic.localfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class LocalFileReader implements LocalFileReaderInterface{
	@Override
	public List<LocalFileInfo> readPlayerStatsHtmlFiles(){
		List<LocalFileInfo> resultList = new ArrayList();
		final String directory = "C:\\0\\web2";
		 try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
	            paths.filter(Files::isRegularFile)
	                 .forEach(path -> {
	                     try {
	                    	 LocalFileInfo localFileInfo = new LocalFileInfo();
	                         String content = new String(Files.readAllBytes(path));
	                         System.out.println("파일명: " + path.getFileName());
	                         System.out.println(content);
	                         System.out.println("-------------------");
	                         localFileInfo.setFileContent(content);
	                         localFileInfo.setFileName(path.getFileName().toString());
	                         resultList.add(localFileInfo);
	                     } catch (IOException e) {
	                         e.printStackTrace();
	                     }
	                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
		return resultList;
	}
	public String readHtmlFile(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}
}
