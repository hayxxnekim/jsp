package handler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHandler {
	private static final Logger log = LoggerFactory.getLogger(FileHandler.class);
	
	//파일 이름, 경로 기반 파일을 삭제하는 메서드
	public int deleteFile(String imageFileName, String savePath) {
		//삭제가 이루어졌는지 체크하는 변수
		boolean isDel = true;
		log.info("imageFileName >>> "+imageFileName);
		
		//savePath 경로 기반 file 객체 생성(디렉토리 경로)
		File fileDir = new File(savePath);
		
		//파일의 디렉토리가 저장된 파일 객체(fileDir)와,
		//파일 이름(imageFileName)을 합쳐서
		//삭제할 파일을 나타내는 file 객체 생성
		//separator : / 추가
		//File removeFile = new File(new File(savePath), imageFileName);
		File removeFile = new File(fileDir+File.separator+imageFileName);
		File removeThFile = new File(fileDir+File.separator+"_th_"+imageFileName);
		
		//파일의 디렉토리, 삭제하고자 하는 특정 파일 객체를 따로 생성
		
		//삭제할 파일과, 파일의 썸네일이 존재하면 삭제
		if(removeFile.exists() || removeThFile.exists()) {
			//delete() : boolean 리턴
			//삭제 시 ture
			isDel = removeFile.delete();
			log.info(">>> del : "+(isDel?"OK":"Fail"));
			if(isDel) {
				isDel = removeThFile.delete();
				log.info(">>> thFile del : "+(isDel?"OK":"Fail"));
			}
		}
		 log.info("remove File Ok");
		 //파일 삭제 성공시 1 리턴
		 return isDel? 1:0;
	}
}
