package com.yu.house.biz.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * 文件服务操作类
 * @author jinfangyu
 *
 */
@Service
public class FileService {

	// 将文件配置到配置文件
	@Value("${file.path:}")
	private String filePath;

	// 创建一个上传列表路劲
	public List<String> getImgePaths(List<MultipartFile> files) {
		// 获取图片路径
		if (Strings.isNullOrEmpty(filePath)) {
			filePath = getResourcePath();
		}
		List<String> paths = Lists.newArrayList();
		// 对上传的文件进行操作
		files.forEach(file -> {
			File localFile = null;
			if (!file.isEmpty()) {
				try {
					localFile = saveToLocal(file, filePath);
					//localFile=D:\images\1577163511\888.jpg
					//("abc", "a")   = "bc"
					//("abcba", "b") = "a"
					//file==D:\images\1577163511\888.jpg
					//filePath==D:\images
					String path = StringUtils.substringAfterLast(//
							localFile.toString(), filePath);
					System.out.println("--path--保存最后的路径--> "+path.toString()+" <--yyyy");
					paths.add(path);// 保存最后的路径
					//path=== \1577163511\888.jpg
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		return paths;
	}

	/**
	 * 保存图片方法
	 * 
	 * @param file· 1
	 * @param filePath2
	 * @return
	 * @throws IOException
	 */
	private File saveToLocal(MultipartFile file, String filePath2) throws IOException {
		//filePath2==C:/Users/18285/Pictures/haowu/images
		// 获取到图片路径
		File newFile = new File(filePath + "/" + Instant.now().getEpochSecond()// getEpochSecond获取秒数
				+ "/" + file.getOriginalFilename());// getOriginalFilename 获取上传文件的原名
		if (!newFile.exists()) {// 不存在，创建目录
			newFile.getParentFile().mkdirs();// 创建多级目录
			newFile.createNewFile();// 创建临时的文件
		}
		Files.write(file.getBytes(), newFile);
		return newFile;
	}

	/**
	 * 在配置文件为空时：自动保存图像到项目的根目录下
	 * @return
	 */
	private String getResourcePath() {
		File file = new File(".");
		String absolutePath = file.getAbsolutePath();
		return absolutePath;
	}

}
