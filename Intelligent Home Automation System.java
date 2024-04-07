@SpringBootApplication
public class HomeAutomationSystem {
    public static void main(String[] args) {
        SpringApplication.run(HomeAutomationSystem.class, args);
    }

    @RestController
    @RequestMapping("/devices")
    public class DeviceController {
        private final DeviceService deviceService;

        public DeviceController(DeviceService deviceService) {
            this.deviceService = deviceService;
        }

        @GetMapping
        public List<Device> getAllDevices() {
            return deviceService.getAllDevices();
        }

        @PostMapping
        public Device createDevice(@RequestBody Device device) {
            return deviceService.createDevice(device);
        }

        @PostMapping("/{id}/control")
        public void controlDevice(@PathVariable Long id, @RequestBody DeviceCommand command) {
            deviceService.controlDevice(id, command);
        }
    }

    @Service
    public class DeviceService {
        private final DeviceRepository deviceRepository;
        private final MqttClient mqttClient;

        public DeviceService(DeviceRepository deviceRepository, MqttClient mqttClient) {
            this.deviceRepository = deviceRepository;
            this.mqttClient = mqttClient;
        }

        public List<Device> getAllDevices() {
            return deviceRepository.findAll();
        }

        public Device createDevice(Device device) {
            return deviceRepository.save(device);
        }

        public void controlDevice(Long id, DeviceCommand command) {
            Device device = deviceRepository.findById(id).orElseThrow();
            publishCommand(device, command);
        }

        private void publishCommand(Device device, DeviceCommand command) {
            String topic = String.format("home/devices/%s", device.getId());
            mqttClient.publish(topic, command.toString().getBytes());
        }
    }

    @Entity
    @Data
    public class Device {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String type;
        private String location;
    }

    @Data
    public class DeviceCommand {
        private String action;
        private Map<String, Object> parameters;
    }

    @Configuration
    public class MqttConfiguration {
        @Bean
        public MqttClient mqttClient() {
            MqttClient client = new MqttClient("tcp://broker.example.com:1883");
            client.connect();
            return client;
        }
    }

    public interface DeviceRepository extends JpaRepository<Device, Long> {
    }
}
