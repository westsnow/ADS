




public class IP{
	public final static int LENGTH = 32;
	public String origin = null;
	private boolean[] bits = new boolean[32];
	public IP(String ip){
		origin = ip;
		String[] parts = ip.split("\\.");
		if(parts.length != 4)
			throw new RuntimeException("illegal ip address format:" + ip);
		for(int i = 0; i < 4; ++i){
			int n = Integer.parseInt(parts[i]);
			for(int j = 7; j >= 0 && n != 0; --j){
				if( (n & 1) == 1){
					bits[j + i *8] = true;
				}
				n >>= 1;
			}
		}
	}
	public boolean equals(IP ip2){
		for(int i = 0; i < LENGTH; ++i){
			if(ip2.getByte(i) == 0 && bits[i] == true)
				return false;
			if(ip2.getByte(i) == 1 && bits[i] == false)
				return false;
		}
		return true;
	}
	public int getByte(int index){
		return bits[index] ? 1:0; 
	}
	public String toString(){
		return origin;
	}
}