package ru.lesson.springsecurity_12_02.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.lesson.springsecurity_12_02.model.entity.User;
import ru.lesson.springsecurity_12_02.model.enums.Role;
import ru.lesson.springsecurity_12_02.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    //вндрить пасворд енкодер
    @GetMapping("/public")
    public String endPointPublic() {
        return "Доступ разрешён всем";
    }

    @GetMapping("/user")
    public String endPointUser() {
        return "Доступ разрешён Users";
    }

    @GetMapping("/admin")
    public String endPointAdmin() {
        return "Доступ разрешён Admins";
    }

    @PostMapping("/create") //принять юзера и добавить
    public String createResource(@RequestBody  User user) {
        //pакодировать паролль
        if(user.getRole() == Role.USER) {
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }else{
            return "Войдите как админ";
        }
        return "Ресурс/пользователь добавлен";
    }


    @PutMapping("/update") //принять и заменить юзера
    public String updateResource(User user) {
       User user1 = userRepository.findByUsername(user.getUsername());
       if(user1 != null) {
           user1.setUsername(user.getUsername());
           userRepository.save(user1);
       }
        return "Ресурс/пользователь успешно обновлён";

    }

    @DeleteMapping("/delete")
    public String deleteResource() {
        return "Ресурс успешно удалён";

    }

    @GetMapping("/info")
    public ResponseEntity<?> info() {
       var body =  Map.of(
               "info","info message",
               "messange","Информация о системе",
               "date", LocalDateTime.now().toString());

       return ResponseEntity.ok()
               .header("Service","REST api")
               .body(body);


    }

}
