package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.ExpertDto;
import org.maktab.home_service_system.controller.util.ChangePasswordHolder;
import org.maktab.home_service_system.controller.util.ExpertSearchHolder;
import org.maktab.home_service_system.controller.util.LoginRequest;
import org.maktab.home_service_system.controller.util.CustomerSearchHolder;
import org.maktab.home_service_system.model.entity.Expert;
import org.maktab.home_service_system.model.service.impl.ExpertServiceImpl;
import org.maktab.home_service_system.model.util.UserState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;

    public ExpertController(ExpertServiceImpl expertService) {
        this.expertService = expertService;
    }

    @PostMapping
    public ResponseEntity<ExpertDto> save(@RequestBody ExpertDto expertDto) {
        return ResponseEntity.ok(expertService.save(expertDto));
    }

//    @PutMapping
//    public ResponseEntity<ExpertDto> update(@RequestBody ExpertDto expertDto){
//        return ResponseEntity.ok(expertService.update(expertDto));
//    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExpertDto> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(expertService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ExpertDto>> findAll() {
        return ResponseEntity.ok(expertService.findAllExpert());
    }

    @GetMapping("/login")
    public ResponseEntity<ExpertDto> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(expertService.login(loginRequest));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ExpertDto>> search(@RequestBody ExpertSearchHolder searchHolder) {
        return ResponseEntity.ok(expertService.gridSearch(searchHolder));
    }

    @GetMapping("/changePassword")
    public ResponseEntity<ExpertDto> changePassword(@RequestBody ChangePasswordHolder holder) {
        return ResponseEntity.ok(expertService.changePassword(holder));
    }

    @GetMapping("/addExpertToService/{expertId}/{serviceId}")
    public ResponseEntity<Boolean> addExpertToService(@PathVariable String expertId, @PathVariable String serviceId) {
        return ResponseEntity.ok(
                expertService.addExpertToService(
                        Integer.valueOf(expertId),
                        Integer.valueOf(serviceId)
                ));
    }

    @GetMapping("/deleteExpertOfService/{expertId}/{serviceId}")
    public ResponseEntity<Boolean> deleteExpertOfService(@PathVariable String expertId, @PathVariable String serviceId) {
        return ResponseEntity.ok(
                expertService.deleteExpertOfService(
                        Integer.valueOf(expertId),
                        Integer.valueOf(serviceId)
                ));
    }

    @GetMapping("/findByUserState/{userState}")
    public ResponseEntity<List<ExpertDto>> findByUserState(@PathVariable String userState) {
        return ResponseEntity.ok(
                expertService.findByUserState(UserState.valueOf(userState))
        );
    }

    @GetMapping("/findAllExpertWithWaitingForConfirmationState")
    public ResponseEntity<List<ExpertDto>> findAllExpertWithWaitingForConfirmationState() {
        return ResponseEntity.ok(
                expertService.findAllExpertWithWaitingForConfirmationState()
        );
    }
}
