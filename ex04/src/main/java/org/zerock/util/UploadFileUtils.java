package org.zerock.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

//파일 업로드 기능의 설계.
public class UploadFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString()+"_"+originalName;
		String savedPath = calcPath(uploadPath);
		
		File target = new File(uploadPath+savedPath, savedName);
		
		//원본파일 저장
		FileCopyUtils.copy(fileData, target);
		
		//확장자 가져오기
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		
		String uplaodFileName = null;
		if(MediaUtils.getMediaType(formatName)!=null) {
			uplaodFileName = makeThumnail(uploadPath,savedPath,savedName);
		}else {
			uplaodFileName = makeIcon(uploadPath,savedPath,savedName);
		}
		
		logger.info("uplaodFileName : " + uplaodFileName);
		
		return uplaodFileName;
	}
	
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception{
		String iconName = uploadPath + path + File.separator + fileName;
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	//년월일 폴더의 생성부분
	public static String calcPath(String uploadPath){
		
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		String monthPath=yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath,yearPath,monthPath,datePath);
		logger.info(datePath);
		
		return datePath;
	}
	
	private static void makeDir(String uploadPath, String...paths) {
		if(new File(uploadPath + paths[paths.length-1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath+path);
			
			//각 날짜별로 디렉토리를 하나씩 생성.
			if(!dirPath.exists()) {
				dirPath.mkdirs();
			}
		}
	}
	
	//썸네일 이미지를 생성하는 메소드
	/*uploadPath : 기본경로
	 * path : 년/월/일 폴더
	 * fileName : 현재 업로드된 파일의 이름
	 */
	private static String makeThumnail(String uploadPath, String path, String fileName) throws Exception{
		
		//BufferedImage : 메모리상의 이미지
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path,fileName));
		
		//FIT_TO_HEIGHT : 썸네일 이미지 파일의 높이를 뒤에 지정한 100px로 동일하게 만듬.
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		
		//원본파일과 썸네일파일의 차이는 중간의 s_ 를 구분자로 넣어준것.
		String thumnailName = uploadPath + path + File.separator + "s_"+ fileName;
		
		logger.info("thumnailName : " + thumnailName);
		
		File newFile = new File(thumnailName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		logger.info("formatName : " + formatName);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		return thumnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
		
	
}
