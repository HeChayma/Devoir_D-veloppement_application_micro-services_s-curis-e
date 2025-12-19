package com.example.test_oidc.controller;

import com.example.test_oidc.TestOidcApplication;
import com.example.test_oidc.entites.Courses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    // GET /courses → STUDENT + ADMIN
    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public List<Courses> getCourses() {

        return TestOidcApplication.COURSES;

    }

    // POST /courses → ADMIN seulement
    @PostMapping("/courses")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCourse(@RequestBody Map<String, String> body) {
        Long id = (long) (TestOidcApplication.COURSES.size() + 1);
        Courses course = new Courses(id, body.get("name") , body.get("Teacher"));
        TestOidcApplication.COURSES.add(course);

        return "Cours ajouté : " + course.getName();
    }

    // GET /me → infos du token
    @GetMapping("/me")
    public Map<String, Object> getMe(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", claims.get("sub"));                  // ID de l'utilisateur
        userInfo.put("username", claims.get("preferred_username")); // Nom d'utilisateur
        userInfo.put("name", claims.get("name"));              // Nom complet
        userInfo.put("email", claims.get("email"));            // Email

        // Récupérer les rôles depuis realm_access
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess != null && realmAccess.get("roles") != null) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            List<String> filteredRoles = roles.stream()
                    .filter(role -> role.equals("STUDENT") || role.equals("ADMIN"))
                    .toList(); // Java 16+ ou utiliser collect(Collectors.toList()) pour les versions antérieures
            userInfo.put("roles", filteredRoles);
        } else {
            userInfo.put("roles", Collections.emptyList());
        }

        return userInfo;
    }
}