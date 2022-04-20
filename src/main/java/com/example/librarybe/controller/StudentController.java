package com.example.librarybe.controller;

import com.example.librarybe.model.Student;
import com.example.librarybe.model.dto.BookDTO;
import com.example.librarybe.model.dto.StudentDTO;
import com.example.librarybe.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/students")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<StudentDTO>> findAll() {
        List<Student> studentList = studentService.findAll();
        if (studentList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (int i = 0; i < studentList.size(); i++) {
            StudentDTO studentDTO = new StudentDTO();
            List<BookDTO> bookDTOList = new ArrayList<>();
            studentDTO.setId(studentList.get(i).getId());
            studentDTO.setName(studentList.get(i).getName());
            for (int j = 0; j < studentList.get(i).getLendingList().size(); j++) {
                for (int k = 0; k < studentList.get(i).getLendingList().get(j).getLendingBookList().size(); k++) {
                    BookDTO bookDTO = new BookDTO();

                    bookDTO.setId(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getId());
                    bookDTO.setIsbn(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getIsbn());
                    bookDTO.setTitle(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getTitle());
                    bookDTO.setCover(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getCover());
                    bookDTO.setAuthors(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getAuthors());
                    bookDTO.setNumber_of_pages(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getNumber_of_pages());
                    bookDTO.setPublisher(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                    bookDTO.setPublisher(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                    bookDTO.setPublish_date(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getPublish_date());
                    bookDTO.setStatus(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getStatus());
                    bookDTO.setRack(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getBook().getRack());

                    bookDTO.setReturnDate(studentList.get(i).getLendingList().get(j).getLendingBookList().get(k).getReturnDate());
                    bookDTOList.add(bookDTO);
                }
            }
            studentDTO.setBookDTOList(bookDTOList);
            studentDTOList.add(studentDTO);
        }
        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable int id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (int j = 0; j < student.getLendingList().size(); j++) {
            for (int k = 0; k < student.getLendingList().get(j).getLendingBookList().size(); k++) {
                BookDTO bookDTO = new BookDTO();

                bookDTO.setId(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getId());
                bookDTO.setIsbn(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getIsbn());
                bookDTO.setTitle(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getTitle());
                bookDTO.setCover(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getCover());
                bookDTO.setAuthors(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getAuthors());
                bookDTO.setNumber_of_pages(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getNumber_of_pages());
                bookDTO.setPublisher(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                bookDTO.setPublisher(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublisher());
                bookDTO.setPublish_date(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getPublish_date());
                bookDTO.setStatus(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getStatus());
                bookDTO.setRack(student.getLendingList().get(j).getLendingBookList().get(k).getBook().getRack());

                bookDTO.setLoanDate(student.getLendingList().get(j).getLendingBookList().get(k).getLending().getLoanDate());

                bookDTO.setReturnDate(student.getLendingList().get(j).getLendingBookList().get(k).getReturnDate());
                bookDTOList.add(bookDTO);
            }
        }
        studentDTO.setBookDTOList(bookDTOList);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody Student student) {
        studentService.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
