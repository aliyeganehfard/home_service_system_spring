package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.AdminDto;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.model.service.impl.AdminServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminDto> save(@RequestBody AdminDto admin) {
        return ResponseEntity.ok(adminService.save(admin));
    }

    @PutMapping
    public ResponseEntity<AdminDto> update(@RequestBody AdminDto admin) {
        return ResponseEntity.ok(adminService.update(admin));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteById(@PathVariable(name = "id") Integer id) {
        adminService.deleteById(id);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AdminDto>> findAll() {
        return ResponseEntity.ok(adminService.findAllAdmin());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AdminDto> findById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(adminService.findById(id));
    }

    @GetMapping("/login")
    public ResponseEntity<AdminDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(adminService.login(loginRequest));
    }

    @GetMapping("/setExpertState/{id}/{userState}")
    public ResponseEntity<Boolean> setExpertState(@PathVariable String id,@PathVariable String userState){
        return ResponseEntity.ok(adminService.setExpertState(Integer.valueOf(id),userState));
    }

    @GetMapping("/confirmExpert/{id}")
    public ResponseEntity<Boolean> confirmExpert(@PathVariable String id){
        return ResponseEntity.ok(adminService.confirmExpert(Integer.valueOf(id)));
    }
//    public Re
}
