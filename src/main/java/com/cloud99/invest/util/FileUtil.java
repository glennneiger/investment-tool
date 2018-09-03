package com.cloud99.invest.util;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

	public String getFileContents(String filePath) throws Exception {

		URL resource = FileUtil.class.getResource(filePath);
		Path path = Paths.get(resource.toURI());
		if (path.toFile().exists()) {
			File f = path.toFile();
			String contents = null;
			if (f.exists()) {
				try (InputStream is = new FileInputStream(f)) {
					contents = IOUtils.toString(is, StandardCharsets.UTF_8);
				}
			} else {
				throw new FileNotFoundException("File not found: " + filePath);
			}

			return contents;
		}
		return null;
	}
}
