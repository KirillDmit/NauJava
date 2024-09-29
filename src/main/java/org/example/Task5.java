package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task5 implements Task{

    private final Path sourceFolder;
    private final Path destinationFolder;
    private volatile boolean running = false;
    private ExecutorService executorService;

    public Task5(String sourceDir, String destinationDir) {
        this.sourceFolder = Paths.get(sourceDir);
        this.destinationFolder = Paths.get(destinationDir);
    }

    @Override
    public void start() {
        if (running) {
            System.out.println("Синхронизация уже запущена.");
            return;
        }
        running = true;
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::syncFolders);
        System.out.println("Синхронизация начата.");
    }

    @Override
    public void stop() {
        if (!running) {
            System.out.println("Синхронизация не запущена.");
            return;
        }
        running = false;
        executorService.shutdownNow();
        System.out.println("Синхронизация остановлена.");
    }

    private void syncFolders() {
        try {
            // Выполняем начальную синхронизацию всех файлов
            Files.walkFileTree(sourceFolder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    syncFile(file);
                    return FileVisitResult.CONTINUE;
                }
            });

            // Запуск WatchService для отслеживания изменений
            WatchService watchService = FileSystems.getDefault().newWatchService();
            sourceFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            while (running) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changedFile = (Path) event.context();
                    Path fullPath = sourceFolder.resolve(changedFile);
                    if (Files.isRegularFile(fullPath)) {
                        syncFile(fullPath);
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    private void syncFile(Path sourceFile) {
        Path destinationFile = destinationFolder.resolve(sourceFolder.relativize(sourceFile));
        try {
            Files.createDirectories(destinationFile.getParent());
            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Синхронизирован файл: " + sourceFile);
        } catch (IOException e) {
            System.err.println("Ошибка при синхронизации файла: " + sourceFile);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Task5 syncTask = new Task5("C:\\Java Projects\\NauJava\\src\\main\\resources\\test1",
                "C:\\Java Projects\\NauJava\\src\\main\\resources\\test2");
        syncTask.start();

        // Для примера остановим синхронизацию через 3 секунды
        Thread.sleep(3000);
        syncTask.stop();
    }
}
