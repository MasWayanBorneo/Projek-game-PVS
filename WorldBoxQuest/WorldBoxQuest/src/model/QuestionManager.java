package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionManager {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public QuestionManager() {
        loadQuestions();
    }

    private void loadQuestions() {

        questions.add(new Question(1,
                "Hewan yang hidup di air",
                "IKAN",
                List.of("I", "K", "A", "N")));

        questions.add(new Question(2,
                "Benda yang digunakan untuk menulis",
                "PENA",
                List.of("P", "E", "N", "A")));

        questions.add(new Question(3,
                "Bintang pusat tata surya",
                "MATAHARI",
                List.of("M", "A", "T", "A", "H", "A", "R", "I")));

        questions.add(new Question(4,
                "Buah berwarna merah atau hijau",
                "APEL",
                List.of("A", "P", "E", "L")));

        questions.add(new Question(5,
                "Hewan yang suka menggonggong",
                "ANJING",
                List.of("A", "N", "J", "I", "N", "G")));

        questions.add(new Question(6,
                "Alat elektronik untuk melihat siaran",
                "TV",
                List.of("T", "V")));

        questions.add(new Question(7,
                "Negara dengan Menara Eiffel",
                "PERANCIS",
                List.of("P", "E", "R", "A", "N", "C", "I", "S")));

        questions.add(new Question(8,
                "Planet merah",
                "MARS",
                List.of("M", "A", "R", "S")));

        questions.add(new Question(9,
                "Hewan dengan belalai panjang",
                "GAJAH",
                List.of("G", "A", "J", "A", "H")));

        questions.add(new Question(10,
                "Alat musik dengan tuts hitam putih",
                "PIANO",
                List.of("P", "I", "A", "N", "O")));
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) return null;
        return questions.get(random.nextInt(questions.size()));
    }
}
