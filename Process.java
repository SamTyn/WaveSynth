
public class Process {
	public boolean startProcessing=false;
	public boolean stopProcessing=false;
	public static Process[] waves=new Process[WaveSynthGUI.maxWaves];
	public int startPos=0;
	public double phDist=65535;
	public double[] iirFilter=new double[2];
	public double filterOutput;
	double waveFormFactor;
	public Wave w;
	
	public Process(){
		Thread t=new Thread(new Runnable()
	    {
	        public void run()
	        {
	        	while(true){
	        		try {
						Thread.sleep(5);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
	        		if(startProcessing){
	        			iirFilter[0]=0;
	        			double pos=0;
	        			double posFM=0;
	        			double posT=0;
	        			for(int i=0;i<w.intLength;i++){
	        				if(stopProcessing){
	        					break;
	        				}
	        				posFM+=w.frequencyFM*(double)131068/44100;
	        				posFM=posFM%131068+(pos<0?131068:0);
	        				posT+=w.trembleFrequency*(double)131068/44100;
	        				posT=posT%131068+(pos<0?131068:0);
	        				pos+=modulate(frequency(i),posFM,i)*(double)131068/44100;
	        				pos=pos%131068+(pos<0?131068:0);
	        				try{
	        					w.intData[i]=setAmplitude(reverb(iirFilter(compress2(tremble(waveForm(phaseDistort(pos,i),i),posT,i), i)),i),i);
	        				}catch(Exception e){
	        					e.printStackTrace();
	        				}
	        			}
	        			w.repaint();
	        		}
	        		startProcessing=false;
        			stopProcessing=false;
	        	}
	        }
	    });
		t.setDaemon(true);
		t.start();
	}
	
	public int iirFilter(int data){
		iirFilter[1]=iirFilter[0];
		filterOutput=(1-w.smoothFactor)*data;
		for(int i=1;i<iirFilter.length-2;i++){
			iirFilter[i+1]=iirFilter[i];
			filterOutput+=iirFilter[i]/(iirFilter.length-2)*w.smoothFactor;
		}
		filterOutput+=iirFilter[iirFilter.length-1]/(iirFilter.length-1)*w.smoothFactor;
		iirFilter[0]=filterOutput;
		return (int)filterOutput;
	}
	
	public int setAmplitude(int data, double i){
		return (int) (w.effects.amplitude.dadsrd.value(i)*((double)data));
	}
	
	public int phaseDistort(double pos, double i){
		phDist=65535+(w.effects.phaseDistortion.dadsrd.value(i)*(131070-65535))*(w.effects.phaseDistortion.midPos>65535?1:-1);
		if(pos<phDist){
			return (int)(pos*65535/phDist);
		}else{
			return (int)(65535+(pos-phDist)*65535/(131070-phDist));
		}
	}
	
	public int compress(double data){
		double scale=w.threshold+(1-w.threshold)*w.comprFactor;
		if(data>w.threshold*32767){
			return(int)((w.threshold*32767+(data-w.threshold*32767)*w.comprFactor)/scale);
		}
		else if(-data>w.threshold*32767){
			return (int)((-w.threshold*32767+(data+w.threshold*32767)*w.comprFactor)/scale);
		}else{
			return (int)(data/scale);
		}
	}
	
	public int compress2(double data, double i){
		double factor=1-w.effects.clip.dadsrd.value(i);
		double scale=w.effects.clip.threshold+(1-w.effects.clip.threshold)*factor;
		if(data>w.effects.clip.threshold*32767){
			return(int)((w.effects.clip.threshold*32767+(data-w.effects.clip.threshold*32767)*factor)/scale);
		}
		else if(-data>w.effects.clip.threshold*32767){
			return (int)((-w.effects.clip.threshold*32767+(data+w.effects.clip.threshold*32767)*factor)/scale);
		}else{
			return (int)(data/scale);
		}
	}
	
	public double frequency(double i){
		if(!w.effects.frequency.dadsrd.overPeak(i)){
			if(w.effects.frequency.dadsrd.decay!=0){
				return w.effects.frequency.dadsrd.value(i)*w.peakFrequency;
			}else{
				return w.effects.frequency.dadsrd.value(i)*2*w.baseFrequency;
			}
		}else if(w.effects.frequency.dadsrd.overSustain(i)){
			return w.effects.frequency.dadsrd.value(i)*2*w.baseFrequency;
		}else{
			return w.baseFrequency+(w.peakFrequency-w.baseFrequency)*2*(w.effects.frequency.dadsrd.value(i)-0.5);
		}
	}
	
	public double modulate(double freq, double posFM, double i){
		return freq+2*w.effects.frequencyModulation.dadsrd.value(i)*w.factorFM*WaveTables.sine[(int)posFM]/32767.0;
	}
	
	public int tremble(double data, double posT, double i){
		double trembleFactor=w.effects.tremble.dadsrd.value(i);
		return (int) ((1-WaveTables.sine[(int)posT]/32767.0*trembleFactor)/(1+trembleFactor)*((double)data));
	}
	
	public int reverb(double data, double i){
		double reverbValue=w.effects.reverb.dadsrd.value(i);
		if(i<w.reverbDistance){
			return (int) ((1-reverbValue)*data);
		}else{
			return (int) ((1-reverbValue)*data+reverbValue*w.intData[(int)(i-w.reverbDistance)]);
		}
	}
	
	public double waveForm(int pos, double i){
		waveFormFactor=w.effects.waveForm.dadsrd.value(i);
		if(w.effects.waveForm.waveForm.equals("sine")){
			return w.waveForm[pos]*(1-waveFormFactor)+waveFormFactor*WaveTables.sine[pos];
		}else if(w.effects.waveForm.waveForm.equals("saw")){
			return w.waveForm[pos]*(1-waveFormFactor)+waveFormFactor*WaveTables.saw[pos];
		}else if(w.effects.waveForm.waveForm.equals("square")){
			return w.waveForm[pos]*(1-waveFormFactor)+waveFormFactor*WaveTables.square[pos];
		}else if(w.effects.waveForm.waveForm.equals("triangle")){
			return w.waveForm[pos]*(1-waveFormFactor)+waveFormFactor*WaveTables.triangle[pos];
		}else{
			return 0;
		}
	}
	
}
