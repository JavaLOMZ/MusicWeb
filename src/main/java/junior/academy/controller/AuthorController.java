package junior.academy.controller;

import junior.academy.domain.Author;
import junior.academy.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable int authorId) {
        if (authorService.isAuthorPresent(authorId)) {
            return new ResponseEntity<>(authorService.getAuthorById(authorId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping()
    public void createOrUpdateAuthor(@RequestBody Author author) {
        authorService.createOrUpdateAuthor(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity deleteAuthor(@PathVariable long authorId) {
        if (authorService.isAuthorPresent(authorId)) {
            authorService.deleteAuthorById(authorId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
