import java.io.FileOutputStream;
import java.io.IOException;

public class WAV {

	public static void createFile(byte[] data, String filepath){
		byte[] total = new byte[data.length+44];
		byte[] header =wavHeader(data.length);
		for(int i=0;i<44;i++){
			total[i]=header[i];
		}
		for(int i=0;i<data.length;i++){
			total[i+44]=data[i];
		}
		try{
			FileOutputStream file = new FileOutputStream(filepath);
			file.write(total);
			file.close();
		}catch(IOException e){
			System.out.println("Failed to create audio file");
			System.out.println("Check if the fail isn't opened elsewhere.");
		}
	}
	
	public static void createFile(int[] intData, String filepath){
		byte[] data=new byte[intData.length*2-1];
		for(int i=0;i<data.length-1;i+=2){
			byte[] intToBytes=intToByteArray(intData[i/2]);
			data[i]=intToBytes[0];
			data[i+1]=intToBytes[1];
		}
		createFile(data, filepath);
	}
	
	public static byte[] wavHeader(long datasize){					//Alle getallen zijn little-endian, woorden big-endian.
		byte[] chunkSize=longToByteArray(datasize+36);		//De methodes long-int ToByteArray returnen dus little-endian arrays.
		byte[] subchunk1Size=longToByteArray(16);			//De bits in de bytes zelf zijn big-endian.
		byte[] sampleRate=longToByteArray(44100);			//
		byte[] byteRate=longToByteArray(88200);				//(sampleRate*bitsPerSample*numChannels)/8
		byte[] subchunk2Size=longToByteArray(datasize);		//
		byte[] formatType=intToByteArray(1);				//1=pcm,6=mulaw,7=alaw
		byte[] numChannels=intToByteArray(1);				//
		byte[] blockAlign=intToByteArray(2);				//(bitsPerSample*numChannels)/8
		byte[] bitsPerSample=intToByteArray(16);
		byte[] header= new byte[44];
		header[0]='R';
		header[1]='I';
		header[2]='F';
		header[3]='F';
		header[4]=chunkSize[0];
		header[5]=chunkSize[1];
		header[6]=chunkSize[2];
		header[7]=chunkSize[3];
		header[8]='W';
		header[9]='A';
		header[10]='V';
		header[11]='E';
		header[12]='f';
		header[13]='m';
		header[14]='t';
		header[15]=' ';
		header[16]=subchunk1Size[0];
		header[17]=subchunk1Size[1];
		header[18]=subchunk1Size[2];
		header[19]=subchunk1Size[3];
		header[20]=formatType[0];
		header[21]=formatType[1];
		header[22]=numChannels[0];
		header[23]=numChannels[1];
		header[24]=sampleRate[0];
		header[25]=sampleRate[1];
		header[26]=sampleRate[2];
		header[27]=sampleRate[3];
		header[28]=byteRate[0];
		header[29]=byteRate[1];
		header[30]=byteRate[2];
		header[31]=byteRate[3];
		header[32]=blockAlign[0];
		header[33]=blockAlign[1];
		header[34]=bitsPerSample[0];
		header[35]=bitsPerSample[1];
		header[36]='d';
		header[37]='a';
		header[38]='t';
		header[39]='a';
		header[40]=subchunk2Size[0];
		header[41]=subchunk2Size[1];
		header[42]=subchunk2Size[2];
		header[43]=subchunk2Size[3];
		return header;
	}
	
	public static byte[] longToByteArray(long number){
		byte[] ba= new byte[4];
		ba[0]=(byte)number;
		ba[1]=(byte)(number>>8);
		ba[2]=(byte)(number>>16);
		ba[3]=(byte)(number>>24);
		return ba;
	}
	
	public static byte[] intToByteArray(int number){
		byte[] ba= new byte[2];
		ba[0]=(byte)number;
		ba[1]=(byte)(number>>8);
		return ba;
	}
}
