import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Головний клас програми для виконання лабораторної роботи.
 * Реалізує обробку текстових даних з використанням класу StringBuffer.
 */
public class Main {

    /**
     * Точка входу в програму.
     * Виконує розрахунок варіанту та викликає обробку тексту.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("Рокицький Олександр Сергійович");
        System.out.println("Студент групи ІО-33");
        System.out.println("Номер у списку групи: 20\n");

        try {
            // Оголошення та ініціалізація всіх змінних у виконавчому методі
            int studentNumber = 3320;
            int c3 = studentNumber % 3;
            int c17 = studentNumber % 17;

            System.out.println("C3 = " + studentNumber + " mod 3 = " + c3);
            System.out.println("C17 = " + studentNumber + " mod 17 = " + c17);

            System.out.println("\nТип текстових змінних: StringBuffer");
            System.out.println("Дія з текстом: Надрукувати слова без повторень "
                    + "в алфавітному порядку за першою літерою.\n");

            // Вхідні дані (перетворення String -> StringBuffer дозволено для вводу)
            StringBuffer inputText = new StringBuffer("Я люблю програмування і програмування люблю я");

            // Виклик обробника
            TextProcessor processor = new TextProcessor();
            processor.processText(inputText);

        } catch (Exception e) {
            // Обробка загальних виключень, що могли бути пропущені
            System.err.println("Критична помилка виконання програми: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/**
 * Клас для обробки тексту згідно з варіантом завдання.
 */
class TextProcessor {

    /**
     * Виконує обробку тексту: знаходить унікальні слова та сортує їх.
     * Метод працює виключно з типом StringBuffer для маніпуляцій даними.
     *
     * @param textBuffer вхідний текст у форматі StringBuffer
     */
    public void processText(StringBuffer textBuffer) {
        try {
            if (textBuffer == null || textBuffer.length() == 0) {
                throw new IllegalArgumentException("Текст не може бути null або порожнім.");
            }

            // Список для зберігання слів у форматі StringBuffer
            List<StringBuffer> words = new ArrayList<>();
            StringBuffer currentWord = new StringBuffer();

            // Прохід по тексту символ за символом
            for (int i = 0; i < textBuffer.length(); i++) {
                char c = textBuffer.charAt(i);

                // Якщо символ є буквою, додаємо до поточного слова
                if (Character.isLetter(c)) {
                    // Приводимо до нижнього регістру без використання String.toLowerCase()
                    currentWord.append(Character.toLowerCase(c));
                } else {
                    // Якщо знайшли роздільник і слово не порожнє -> зберігаємо його
                    if (currentWord.length() > 0) {
                        words.add(new StringBuffer(currentWord)); // Створюємо нову копію буфера
                        currentWord.setLength(0); // Очищаємо буфер для наступного слова
                    }
                }
            }
            // Додаємо останнє слово, якщо текст не закінчується роздільником
            if (currentWord.length() > 0) {
                words.add(new StringBuffer(currentWord));
            }

            // Сортування списку StringBuffer
            // Використовуємо кастомний компаратор, оскільки StringBuffer не реалізує Comparable
            words.sort(new Comparator<StringBuffer>() {
                @Override
                public int compare(StringBuffer sb1, StringBuffer sb2) {
                    int len1 = sb1.length();
                    int len2 = sb2.length();
                    int lim = Math.min(len1, len2);

                    for (int k = 0; k < lim; k++) {
                        char c1 = sb1.charAt(k);
                        char c2 = sb2.charAt(k);
                        if (c1 != c2) {
                            return c1 - c2;
                        }
                    }
                    return len1 - len2;
                }
            });

            System.out.println("Слова без повторень у алфавітному порядку:");

            // Виведення слів без дублікатів
            if (!words.isEmpty()) {
                // Виводимо перше слово
                System.out.println(words.get(0));

                // Далі виводимо слово, тільки якщо воно не дорівнює попередньому
                for (int i = 1; i < words.size(); i++) {
                    StringBuffer prev = words.get(i - 1);
                    StringBuffer curr = words.get(i);

                    // Ручне порівняння вмісту двох StringBuffer для уникнення дублікатів
                    if (compareBuffers(prev, curr) != 0) {
                        // Дозволено неявне перетворення в String тільки для System.out.println
                        System.out.println(curr);
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Помилка вхідних даних: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Помилка під час обробки тексту: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Допоміжний метод для порівняння вмісту двох StringBuffer.
     * Необхідний, тому що StringBuffer.equals() порівнює посилання, а не значення.
     * @param sb1 перший буфер
     * @param sb2 другий буфер
     * @return 0 якщо рівні, інше значення якщо ні
     */
    private int compareBuffers(StringBuffer sb1, StringBuffer sb2) {
        if (sb1.length() != sb2.length()) return sb1.length() - sb2.length();
        for (int i = 0; i < sb1.length(); i++) {
            if (sb1.charAt(i) != sb2.charAt(i)) {
                return sb1.charAt(i) - sb2.charAt(i);
            }
        }
        return 0;
    }
}