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
    int smoke;
    int eye;
    double height;
    double weight;
    double hba1c;
    double sbp;
    double hdlc;
    double tg;
    double uacr;
    String phone;
    double bmi;
    double score;
    String modified,created;



    public void setScore(){
        double score=0;
        score=score+smoke+eye;

        this.bmi=calculateBmi(height,weight);
        if(bmi>=24.0 && bmi<28.0){
            score+=1.5;
        }else if(bmi>=28.0){
            score+=3;
        }

        if(age>=50 && age<60){
            score+=3;
        }else if (age>=60){
            score+=6;
        }


        if(uacr>=10 && uacr<20){
            score+=2;
        }else if(uacr>=20 && uacr<=30){
            score+=4;
        }

        if(hba1c>=7.0 && hba1c<8){
            score+=1.5;
        }else if(hba1c>=8.0 && hba1c<9){
            score+=3;
        }else if(hba1c>=9){
            score+=4.5;
        }

        if(sbp>=130 && sbp<140){
            score+=2;
        }else if(sbp>=140 && sbp<150){
            score+=4;
        }else if(sbp>=150){
            score+=6;
        }

        if(hdlc<1.30 && hdlc>=0){
            score+=2.5;
        }

        if(tg>=1.70){
            score+=4;
        }

        this.score=score;



    }

    public double calculateBmi(double height,double weight){
        double bmi=weight/((height*0.01)*(height*0.01));
        return bmi;
    }


}
