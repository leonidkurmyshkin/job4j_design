package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class CSVReader {
    private final String path;
    private final String delimiter;
    private final String out;
    private final String filter;
    private final static String STDOUT = "stdout";

    public CSVReader(String path, String delimiter, String out, String filter) {
        this.path = path;
        this.delimiter = delimiter;
        this.out = out;
        this.filter = filter;
    }

    public void writeCSV(StringBuilder readingCSV) {
        try (var out = this.out.equals(STDOUT)
                ? System.out
                : new PrintStream(new BufferedOutputStream(new FileOutputStream(this.out)),
                        false, Charset.forName("Windows-1251"))) {
            out.append(readingCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder readCSV() {
        var readingCSV = new StringBuilder();
        try (var in = new Scanner(Path.of(path), Charset.forName("Windows-1251"))
                .useDelimiter(delimiter + "|" + System.lineSeparator())) {
            Set<String> filterColumnsNames = new HashSet<>(Arrays.asList(filter.split(",")));
            var header = readCSVHeader(in, filterColumnsNames::contains);
            if (header.indexesToNames.size() != filterColumnsNames.size()) {
                throw new IllegalArgumentException(String.format(
                        "Не все имена столбцов из фильтра %s найдены в файле %s",
                        filter, path));
            }
            header.indexesToNames.forEach((i, name) -> readingCSV.append(name)
                    .append(getSeparator(i, header)));
            readingCSV.append(readCSVBody(in, header));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readingCSV;
    }

    private String getSeparator(int i, CSVHeader header) {
        return i < header.indexesToNames.size() - 1
                ? delimiter
                : System.lineSeparator();
    }

    private CSVHeader readCSVHeader(Scanner in, Predicate<String> columnFilter) {
        if (!in.hasNextLine()) {
            throw new IllegalArgumentException(String.format("%s - пустой файл", path));
        }
        var columnNames = in.nextLine().split(delimiter);
        Map<Integer, String> indexesToNames = new LinkedHashMap<>();
        for (var i = 0; i < columnNames.length; i++) {
            if (columnFilter.test(columnNames[i])) {
                indexesToNames.put(i, columnNames[i]);
            }
        }
        return new CSVHeader(columnNames.length, indexesToNames);
    }

    private StringBuilder readCSVBody(Scanner in, CSVHeader header) {
        var csvBody = new StringBuilder();
        while (in.hasNext()) {
            for (var i = 0; i < header.columnsNumber; i++) {
                if (!in.hasNext()) {
                    throw new IllegalArgumentException(String.format(
                            "%s - не таблица", path));
                }
                var next = in.next();
                if (header.indexesToNames.containsKey(i)) {
                    csvBody.append(next)
                            .append(getSeparator(i, header));
                }
            }
        }
        return csvBody;
    }

    public static ArgsName validateCommandLine(String[] args) {
        if (args.length != 4) {
            throw  new IllegalArgumentException(String.format("%s%n %s%n %s%n %s%n %s%n %s%n",
                    "Параметры запуска указаны не полностью. Запускайте так:",
                    "java -jar csvReader.jar -path=file.csv -delimiter=; "
                    + "-out=stdout -filter=name,age",
                    "где: path - имя csv-файла",
                    "delimiter - разделитель столбцов данных",
                    "out - вывод на консоль при stdout, или в файл с указанным именем",
                    "filter - столбцы, которые нужно выводить в out"));
        }
        var argsName = ArgsName.of(args);
        var csvName = Path.of(argsName.get("path"));
        if (Files.notExists(csvName)) {
            throw new IllegalArgumentException(String.format(
                    "Файл %s не существует", csvName));
        }
        return argsName;
    }

    private static class CSVHeader {
        private final int columnsNumber;
        private final Map<Integer, String> indexesToNames;

        public CSVHeader(int columnsNumber, Map<Integer, String> indexesToNames) {
            this.columnsNumber = columnsNumber;
            this.indexesToNames = indexesToNames;
        }
    }

    public static void main(String[] args) {
        var argsName = validateCommandLine(args);
        var reader = new CSVReader(argsName.get("path"), argsName.get("delimiter"),
                argsName.get("out"), argsName.get("filter"));
        reader.writeCSV(reader.readCSV());
    }
}
