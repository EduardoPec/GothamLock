package com.wayne.waynesecurity.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wayne.waynesecurity.model.AccessLog;
import com.wayne.waynesecurity.model.Inventory;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.model.enums.AccessResult;
import com.wayne.waynesecurity.model.enums.AccessType;
import com.wayne.waynesecurity.model.enums.InventoryStatus;
import com.wayne.waynesecurity.model.enums.InventoryType;
import com.wayne.waynesecurity.model.enums.Role;
import com.wayne.waynesecurity.repositories.AccessLogRepository;
import com.wayne.waynesecurity.repositories.InventoryRepository;
import com.wayne.waynesecurity.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private AccessLogRepository accessLogRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		String encodedPassword = passwordEncoder.encode("123");
		
		// === USUÁRIOS ===
		User batman = new User(null, "Bruce Wayne", encodedPassword, "bruce@wayne.com", Role.ADMIN_SEGURANCA);
        User alfred = new User(null, "Alfred Pennyworth", encodedPassword, "alfred@wayne.com", Role.GERENTE);
        User robin = new User(null, "Dick Grayson", encodedPassword, "dick@wayne.com", Role.FUNCIONARIO);
        
        userRepository.saveAll(Arrays.asList(batman, alfred, robin));
        
        // === INVENTÁRIO ===
        Inventory batmobile = new Inventory(null, "Batmobile", InventoryType.VEICULO, InventoryStatus.DISPONIVEL);
        Inventory batwing = new Inventory(null, "Batwing", InventoryType.VEICULO, InventoryStatus.MANUTENCAO);
        Inventory batarang = new Inventory(null, "Batarang", InventoryType.EQUIPAMENTO, InventoryStatus.DISPONIVEL);
        Inventory grapnel = new Inventory(null, "Grapnel Gun", InventoryType.DISPOSITIVO, InventoryStatus.EM_USO);
        
        inventoryRepository.saveAll(Arrays.asList(batmobile, batwing, batarang, grapnel));
        
        // === LOGS DE ACESSO ===
        AccessLog entradaBatcave = new AccessLog(null, AccessArea.SALA_BATMAN, AccessType.ENTRADA, AccessResult.AUTORIZADO, Instant.parse("2025-06-20T19:53:07Z"), batman);
        AccessLog saidaBatcave = new AccessLog(null, AccessArea.SALA_BATMAN, AccessType.SAIDA, AccessResult.AUTORIZADO, Instant.parse("2025-06-20T16:35:10Z"), batman);
        AccessLog negadoLab = new AccessLog(null, AccessArea.LABORATORIO, AccessType.ENTRADA, AccessResult.NEGADO, Instant.parse("2025-06-23T22:10:22Z"), robin);
        AccessLog alfredArmazem = new AccessLog(null, AccessArea.ARMAZEM, AccessType.ENTRADA, AccessResult.AUTORIZADO, Instant.parse("2025-07-20T14:40:35Z"), alfred);
        
        accessLogRepository.saveAll(Arrays.asList(entradaBatcave, saidaBatcave, negadoLab, alfredArmazem));
	}
}
