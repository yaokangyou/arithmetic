package main.JudgeAnswer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class answerJudge {
	public static void Judge(String exercisePath, String answersPath) throws IOException {
		List<String> exerciseAnswers = exerciseFileRecording(exercisePath);
        List<String> answers = answerRecording(answersPath);
        
        List<String> correct = new ArrayList<>();
        List<String> wrong = new ArrayList<>();
        int SerialNumber = 1;
        int MIN = Math.min(exerciseAnswers.size(), answers.size());
        
        for (int i = 0; i < MIN; i++){ 
            if (exerciseAnswers.get(i).equals(answers.get(i))){
            	correct.add(String.valueOf(SerialNumber++));
            }else {
            	wrong.add(String.valueOf(SerialNumber++));
            }
        }
        
        File grade = new File("Grade.txt");
        if (grade.exists()){
            grade.delete();
        }
        if (grade.createNewFile()){
	        FileOutputStream gradeOutput = new FileOutputStream(grade);
	        PrintStream gradePrintStream = new PrintStream(gradeOutput);
	        String corrects = String.join(",", correct);
	        gradePrintStream.println("Correct：" + correct.size() +
	                " (" + corrects + ")");
	        String wrongs = String.join(",", wrong);
	        gradePrintStream.println("Wrong：" + wrong.size() +
	                " (" + wrongs + ")");
        }
        System.out.println("判定完成!记录在Grade.txt。");
	}
	
	public static List<String> exerciseFileRecording(String path) throws IOException {
		String exerciseAnswer = "";
        BufferedReader exerciseRecording = new BufferedReader(new FileReader(path));
        List<String> exerciseAnswers = new ArrayList<>();
        while ((exerciseAnswer = exerciseRecording.readLine()) != null){
            String[] split = exerciseAnswer.split("=");
            if (split.length >= 2){
                exerciseAnswers.add(split[1]);
            }else {
                exerciseAnswers.add(" ");
            }
        }
        return exerciseAnswers;
    }
	
	public static List<String> answerRecording(String path) throws IOException {
		String answer = "";
        BufferedReader answerRecording = new BufferedReader(new FileReader(path));
        List<String> answers = new ArrayList<>();
        while ((answer = answerRecording.readLine()) != null){
            String[] split = answer.split(" ");
            answers.add(split[1]);
        }
        return answers;
    }
}
