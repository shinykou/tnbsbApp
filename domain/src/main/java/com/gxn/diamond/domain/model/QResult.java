package com.gxn.diamond.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QResult {
    int id;
    int age;
    String gender;
    int smoke;
    int gaoxueya;
    int tangniaobing;
    int eye;
    double height;
    double weight;
    double hba1c;
    double sbp;
    double hdlc;
    double tg;
    double ldlc;
    double uacr;
    double scr;
    double sua;
    String phone;
    String openId;
    double bmi;
    double score;
    String modified,created;



    public void setScore(){
        double score=0;
        score=score+eye;

        /**
         *不吸烟(0分）
         吸烟（4分）
         */
        if(smoke>0){
            score+=4;
        }

        /**
         * ＜24.00(0分)
         B、24.00~27.99(1.5分)
         C、≥28.00(3分)
         */
        this.bmi=calculateBmi(height,weight);
        if(bmi>=24.0 && bmi<28.0){
            score+=1.5;
        }else if(bmi>=28.0){
            score+=3;
        }

        /**
         * ≤49岁(0分)
         50~59岁(3分)
         ≥60岁(6分)
         */
        if(age>=50 && age<60){
            score+=3;
        }else if (age>=60){
            score+=6;
        }


        /**
         * ＜10.00(0分)
         10.00~19.99(2分)
         20.00~29.99(4分)
         >30(直接爆灯)
         */
        if(uacr>=10 && uacr<20){
            score+=2;
        }else if(uacr>=20 && uacr<=30){
            score+=4;
        }else if (uacr>30){
            score+=37;
        }

        /**
         * ＜7.0(0分)
         7.0~7.9(1.5分)
         8.0~8.9(3分)
         ≥9.0(4.5分)
         */
        if(hba1c>=7.0 && hba1c<8){
            score+=1.5;
        }else if(hba1c>=8.0 && hba1c<9){
            score+=3;
        }else if(hba1c>=9){
            score+=4.5;
        }

        /**
         * ＜130(0分)
         130~139(2分)
         140~149(4分)
         ≥150(6分)
         */
        if(sbp>=130 && sbp<140){
            score+=2;
        }else if(sbp>=140 && sbp<150){
            score+=4;
        }else if(sbp>=150){
            score+=6;
        }

        /**
         * ≥1.30(0分)
         ＜1.30(2.5分)
         */
        if(hdlc<1.30 && hdlc>=0){
            score+=2.5;
        }

        /**
         * ＜1.70(0分)
         ≥1.70(4分)
         */
        if(tg>=1.70){
            score+=4;
        }

        this.score=score;



    }

    public double calculateBmi(double height,double weight){
        double bmi=weight/((height)*(height));
        return bmi;
    }


}
