package top.lijingyuan.grpc.learning.sample;

import com.google.protobuf.Timestamp;
import top.lijingyuan.grpc.learning.pb.*;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * Generator
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-10-19
 * @since 1.0.0
 */
public class Generator {

    private Random rand;

    public Generator() {
        this.rand = new Random();
    }

    public Keyboard newKeyboard() {
        return Keyboard.newBuilder()
                .setLayout(randomKeyboardLayout()).setBacklit(rand.nextBoolean()).build();
    }

    private Keyboard.Layout randomKeyboardLayout() {
        switch (rand.nextInt()) {
            case 1:
                return Keyboard.Layout.QWERTY;
            case 2:
                return Keyboard.Layout.QWERTZ;
            case 3:
                return Keyboard.Layout.AZERTY;
            default:
                return Keyboard.Layout.UNKNOWN;
        }
    }

    public CPU newCpu() {
        String brand = randomCpuBrand();
        String name = randomCpuName(brand);

        int numberCores = randomInt(2, 8);
        int numberThread = randomInt(numberCores, 12);

        double minGhz = randomDouble(2.0, 3.5);
        double maxGhz = randomDouble(minGhz, 5.0);

        return CPU.newBuilder().setBrand(brand).setName(name).setNumberCores(numberCores)
                .setNumberCores(numberThread).setMinGhz(minGhz).setMaxGhz(maxGhz)
                .build();
    }

    public GPU newGpu() {
        String brand = randomGpuBrand();
        String name = randomGpuName(brand);

        double minGhz = randomDouble(1.0, 1.5);
        double maxGhz = randomDouble(minGhz, 2.0);

        Memory memory = Memory.newBuilder()
                .setValue(randomInt(2, 6)).setUnit(Memory.Unit.GIGABYTE)
                .build();

        return GPU.newBuilder()
                .setBrand(brand).setName(name).setMinGhz(minGhz).setMaxGhz(maxGhz).setMemory(memory)
                .build();
    }

    public Memory newRam() {
        return Memory.newBuilder()
                .setValue(randomInt(4, 64)).setUnit(Memory.Unit.GIGABYTE)
                .build();
    }

    public Storage newSSD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(128, 1024)).setUnit(Memory.Unit.GIGABYTE)
                .build();
        return Storage.newBuilder()
                .setMemory(memory).setDriver(Storage.Driver.SSD)
                .build();
    }

    public Storage newHDD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(1, 6)).setUnit(Memory.Unit.TERABYTE)
                .build();
        return Storage.newBuilder()
                .setMemory(memory).setDriver(Storage.Driver.HDD)
                .build();
    }

    public Screen newScreen() {
        int height = randomInt(1080, 4320);
        int width = height * 16 / 9;

        Screen.Resolution resolution = Screen.Resolution.newBuilder()
                .setHeight(height).setWidth(width)
                .build();
        return Screen.newBuilder()
                .setSizeInch(randomFloat(13, 17))
                .setResolution(resolution)
                .setPanel(randomScreenPanel())
                .setMultiTouch(rand.nextBoolean())
                .build();
    }

    public Laptop newLaptop() {
        String brand = randomLaptopBrand();
        String name = randomLaptopName(brand);

        double weightKg = randomDouble(1.0, 3.0);
        double priceUsd = randomDouble(1500, 3500);

        int releaseYear = randomInt(2015, 2021);

        return Laptop.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setBrand(brand)
                .setName(name)
                .setCpu(newCpu())
                .setRam(newRam())
                .addGpus(newGpu())
                .addStorages(newSSD())
                .addStorages(newHDD())
                .setScreen(newScreen())
                .setKeyboard(newKeyboard())
                .setWeightKg(weightKg)
                .setPriceUsed(priceUsd)
                .setReleaseYear(releaseYear)
                .setUpdatedAt(timestampNow())
                .build();
    }

    private Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    private String randomLaptopName(String brand) {
        switch (brand) {
            case "Apple":
                return randomStringFromSet("Macbook Air", "Macbook Pro");
            case "Dell":
                return randomStringFromSet("Latitude", "Vostro", "XPS", "Alienware");
            default:
                return randomStringFromSet("Thinkpad X1", "Thinkpad P1", "Thinkpad P53");
        }
    }

    private String randomLaptopBrand() {
        return randomStringFromSet("Apple", "Dell", "Lenovo");
    }

    private Screen.Panel randomScreenPanel() {
        return rand.nextBoolean() ? Screen.Panel.IPS : Screen.Panel.OLED;
    }

    private float randomFloat(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }

    private String randomGpuName(String brand) {
        if ("NVIDIA".equals(brand)) {
            return randomStringFromSet(
                    "RTX 2060",
                    "RTX 2070",
                    "GTX 1660-Ti",
                    "GTX 1070"
            );
        }

        return randomStringFromSet(
                "RX 590",
                "RX 580",
                "RX 5700-XT",
                "RX Vega-56"
        );
    }

    private String randomGpuBrand() {
        return randomStringFromSet("NVIDIA", "AMD");
    }


    private double randomDouble(double min, double max) {
        return min + rand.nextDouble() * (max - min);
    }

    private int randomInt(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    private String randomCpuName(String brand) {
        if ("Intel".equals(brand)) {
            return randomStringFromSet(
                    "Xeon E-2286M",
                    "Core i9-9980HK",
                    "Core i7-9750H",
                    "Core i5-9400F",
                    "Core i3-1005G1"
            );
        }

        return randomStringFromSet(
                "Ryzen 7 PRO 2700U",
                "Ryzen 5 PRO 3500U",
                "Ryzen 3 PRO 3200GE"
        );
    }

    private String randomCpuBrand() {
        return randomStringFromSet("Intel", "AMD");
    }

    private String randomStringFromSet(String... a) {
        int n = a.length;
        if (n == 0) {
            return "";
        }
        return a[rand.nextInt(n)];
    }

    public double newLaptopScore() {
        return randomInt(1, 10);
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop();
        System.out.println(laptop);
    }

}
