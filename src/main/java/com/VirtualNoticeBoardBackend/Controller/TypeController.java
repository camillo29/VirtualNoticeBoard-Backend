package com.VirtualNoticeBoardBackend.Controller;

import com.VirtualNoticeBoardBackend.Model.Type;
import com.VirtualNoticeBoardBackend.Repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/TypeController")
public class TypeController {
    @Autowired
    TypeRepository typeRepository;

    @GetMapping("/types")
    public ResponseEntity<List<Type>> getTypes(){
        return ResponseEntity.ok().body(typeRepository.findAll());
    }
}
