public class BowlingGame {

	public static final int totalRound = 10;    // �ܾ�����Ĭ����10

    private int[] scores;                        // ��¼ÿ�ֵ÷ֵ�����
    private int[][] hitsNum;                // ��¼ÿ���������ƿ�Ӹ���������
    private int lastOneHit, lastTwoHit;            // ������λ������
    private int totalScore;                        // �ܷ���

    private int currentRound;                    // ��ǰ�ڼ���
    private boolean isFirstHit;                    // �Ƿ���ÿ�ֵĵ�һ�λ���

    public BowlingGame() {
        scores = new int[totalRound];
        hitsNum = new int[totalRound][2];
        isFirstHit = true;
    }

    /**
     * �ӳ�һ����
     * @param num ��ƿ�ӵ�����
     */
    public void throwTheBall(int num) {
        if(isFirstHit) {    // ��һ����
            hitsNum[currentRound][0] = num;
            if(num != 10) {
                isFirstHit = false;    // ��һ����û��ȫ����Ҫ��ڶ���
            }
            else {
                currentRound += 1;    // ��һ����ȫ��ֱ�ӽ�����һ��
            }
        }
        else {
            hitsNum[currentRound][1] = num;
            currentRound += 1;        // ����ڶ����������һ��
            isFirstHit = true;        // ׼��Ͷ����һ�ֵĵ�һ����
        }
    }
    
    /**
     * ����ÿ�ֵĵ÷�
     */
    public void calcScore() {
        // �ӵ�һ�ֵ��ھž�
        for(int i = 0; i < hitsNum.length - 1; i++) {
            if(hitsNum[i][0] == 10) {    // Strike
                scores[i] += 10;
                if(hitsNum[i + 1][0] == 10) {
                    scores[i] += 10;
                    if(i < 8) {
                        scores[i] += hitsNum[i + 2][0];
                    }
                    else {
                        scores[i] += lastOneHit;
                    }
                }
                else {
                    scores[i] += hitsNum[i + 1][0] + hitsNum[i + 1][1];
                }
            }
            else if(hitsNum[i][0] + hitsNum[i][1] == 10) { // Spare
                scores[i] += 10;
                scores[i] += hitsNum[i + 1][0];
            }
            else {
                scores[i] += hitsNum[i][0] + hitsNum[i][1];
            }
        }
        // ���һ��
        if(hitsNum[9][0] == 10) {
            scores[9] += 10;
            scores[9] += lastOneHit + lastTwoHit;
        }
        else if(hitsNum[9][0] + hitsNum[9][1] == 10) {
            scores[9] += 10;
            scores[9] += lastOneHit;
        }
        else {
            scores[9] += hitsNum[9][0] + hitsNum[9][1];
        }
    }

   /**
    * ����һ��������������ܷ���
    * @param bowingCode һ����������������
    * @return �ܷ���
    */
    public int getBowlingScore(String bowlingCode) {  
    	//��.���͡�|������ת���ַ�������ü�"\\";
    	String[] bowlingCodes = bowlingCode.split("\\|");
    	int i;
    	currentRound = 0;
    	for(i=0;i<10;i++){    //����ǰʮ�ֵı������
    		char[] score = bowlingCodes[i].toCharArray();
    		if(score[0]=='X'){  //strike
    			throwTheBall(10);
    		}else if(score[0]=='-'){  //��һ��δ����
    			throwTheBall(0);
    			if(score[1]=='-'){   //�ڶ���δ����
    				throwTheBall(0);
    			}else if(score[1]=='/'){  //spare 
    				throwTheBall(10);
    			}else{
    				throwTheBall(Integer.parseInt(score[1]+""));  //�ڶ�����еĸ���
    			}
    		}else{  //��һ�����
    			int firstScore = Integer.parseInt(score[0]+"");
    			throwTheBall(firstScore); 
    			if(score[1]=='-'){   //�ڶ���δ����
    				throwTheBall(0);
    			}else if(score[1]=='/'){  //spare 
    				throwTheBall(10-firstScore);
    			}else{
    				throwTheBall(Integer.parseInt(score[1]+""));  //�ڶ�����еĸ���
    			}
    		}
    	}
    	//����ı������
    	if(bowlingCodes.length>10){
    		char[] finalScore = bowlingCodes[bowlingCodes.length-1].toCharArray();
    		if(finalScore.length!=0){ 
        		if(finalScore[0]=='X'){
        			lastOneHit=10;
        		}else if(finalScore[0]=='-'){
        			lastOneHit=0;
        		}else{
        			lastOneHit = Integer.parseInt(finalScore[0]+"");
        		}
        	}
        	if(finalScore.length==2){
        		if(finalScore[1]=='X'){
        			lastTwoHit=10;
        		}else if(finalScore[1]=='-'){
        			lastTwoHit=0;
        		}else if(finalScore[1]=='/'){
        			lastTwoHit = 10-lastOneHit;
        		}
        		else{
        			lastTwoHit = Integer.parseInt(finalScore[1]+"");
        		}
        	}
    	}
       //����ÿ�ֱ����ķ���
    	calcScore();
    	//�����ܷ���
    	 for(i = 0; i < scores.length; i++) {
             totalScore += scores[i];
         }
         return totalScore;
    }
}
