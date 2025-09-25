package dk.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Вспомогательный микс-класс с полезными методами.<br>
 * Возможно, в дальнейшем, некоторые методы будут сгрупированы в отдельные классы, по схожей функциональности. Но это потом...
 */
public class HelpService {

    /**
     * Получить inode файла
     * <p>
     * <b style="color:red">Внимание!<br>
     * Следует использовать данный метод крайне осторожно. Отладка метода делалась для комбинации linux + java 8(компиляция) + java 8(выполнение).
     * На windows метод не работает, поскольку inode на windows не существует.
     * При использовании не 8-й java у клиента, наблюдались проблемы с отработкой рефлексии.
     * </b>
     * </p>
     * @param fileName Имя файла, чей inode требуется вычитать
     * @return inode либо -1, если вычитать inode не удалось
     * @throws IOException генерируется при проблеме доступа к файлу
     * @throws NoSuchFieldException проблемы при доступе к полю при использовании рефлексии
     * @throws IllegalAccessException проблемы при вычитке значения из поля через рефлексию     *
     */
    public long getInode(String fileName) throws IOException, NoSuchFieldException, IllegalAccessException {
        Path filePath = FileSystems.getDefault().getPath(fileName);
        Object fileKey = Files.getAttribute(filePath, "basic:fileKey");

        if (fileKey != null /* дань windows */) {
            Field st_ino = fileKey.getClass().getDeclaredField("st_ino");
            st_ino.setAccessible(true);
            return st_ino.getLong(fileKey);
        } else {
            return -1;
        }
    }


    /**
     * Получить имя файла без расширения
     * @param fileName имя файла(возможно с расширением)
     * @return имя файла без расширения
     */
    public String getFileNamePart(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }


    /**
     * Получить имя файла на основе имени другого файла
     * @param fileName Имя файла без папки, которое, нужно по структуре(папка + имя) довести до имени файла fileNameTemplate
     * @param fileNameTemplate Имя файла, которое рассматривается как шаблон
     * @return Имя файла fileName доведенное по структуре(папка + имя) до структуры fileNameTemplate
     */
    public String getFileNameFromAnotherFileNameTemplate(String fileName, String fileNameTemplate) {

        if (fileNameTemplate.contains(File.separator)) {
            return fileNameTemplate.substring(0, fileNameTemplate.lastIndexOf(File.separator) + 1) + fileName;
        } else {
            return fileName;
        }

    }


    /**
     * Получить расширение файла
     * @param fileName имя файла(возможно с расширением, возможно - нет)
     * @return расширение без точки или пустая строка, в случае отсутствия в имени расширения
     */
    public String getFileExtPart(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }


    /**
     * Сколько раз символ встречается в строке?
     * @param s Строка, в которой производиться поиск.
     * @param c Символ, для которого подсчитывается количество вхождений в строку.
     * @return Количество вхождений символа в строку.
     */
    public int howmanyCharOccur(String s, char c) {
        int cnt = 0;
        for (char ch: s.toCharArray()) {
            if (ch == c) cnt++;
        }
        return cnt;
    }

}
