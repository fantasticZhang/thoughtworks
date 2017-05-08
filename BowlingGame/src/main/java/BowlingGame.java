public class BowlingGame {

	public static final int totalRound = 10;    // 总局数，默认是10

    private int[] scores;                        // 记录每局得分的数组
    private int[][] hitsNum;                // 记录每局两球击倒瓶子个数的数组
    private int lastOneHit, lastTwoHit;            // 最后两次击球情况
    private int totalScore;                        // 总分数

    private int currentRound;                    // 当前第几局
    private boolean isFirstHit;                    // 是否是每局的第一次击球

    public BowlingGame() {
        scores = new int[totalRound];
        hitsNum = new int[totalRound][2];
        isFirstHit = true;
    }

    /**
     * 扔出一颗球
     * @param num 打倒瓶子的数量
     */
    public void throwTheBall(int num) {
        if(isFirstHit) {    // 第一个球
            hitsNum[currentRound][0] = num;
            if(num != 10) {
                isFirstHit = false;    // 第一个球没有全中则要打第二颗
            }
            else {
                currentRound += 1;    // 第一个球全中直接进入下一局
            }
        }
        else {
            hitsNum[currentRound][1] = num;
            currentRound += 1;        // 打完第二个球进入下一局
            isFirstHit = true;        // 准备投掷下一局的第一个球
        }
    }
    
    /**
     * 计算每局的得分
     */
    public void calcScore() {
        // 从第一局到第九局
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
        // 最后一局
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
    * 计算一场保龄球比赛的总分数
    * @param bowingCode 一场保龄球比赛的情况
    * @return 总分数
    */
    public int getBowlingScore(String bowlingCode) {  
    	//“.”和“|”都是转义字符，必须得加"\\";
    	String[] bowlingCodes = bowlingCode.split("\\|");
    	int i;
    	currentRound = 0;
    	for(i=0;i<10;i++){    //分析前十局的比赛情况
    		char[] score = bowlingCodes[i].toCharArray();
    		if(score[0]=='X'){  //strike
    			throwTheBall(10);
    		}else if(score[0]=='-'){  //第一球未击中
    			throwTheBall(0);
    			if(score[1]=='-'){   //第二球未击中
    				throwTheBall(0);
    			}else if(score[1]=='/'){  //spare 
    				throwTheBall(10);
    			}else{
    				throwTheBall(Integer.parseInt(score[1]+""));  //第二球击中的个数
    			}
    		}else{  //第一球击中
    			int firstScore = Integer.parseInt(score[0]+"");
    			throwTheBall(firstScore); 
    			if(score[1]=='-'){   //第二球未击中
    				throwTheBall(0);
    			}else if(score[1]=='/'){  //spare 
    				throwTheBall(10-firstScore);
    			}else{
    				throwTheBall(Integer.parseInt(score[1]+""));  //第二球击中的个数
    			}
    		}
    	}
    	//额外的比赛情况
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
       //计算每局比赛的分数
    	calcScore();
    	//计算总分数
    	 for(i = 0; i < scores.length; i++) {
             totalScore += scores[i];
         }
         return totalScore;
    }
}
