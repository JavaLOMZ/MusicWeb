package junior.academy.controller;

import junior.academy.domain.Author;
import junior.academy.service.AuthorService;
import junior.academy.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorValidator authorValidator;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(this.authorValidator);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable long authorId) {
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
    public void createOrUpdateAuthor(@RequestBody @Valid Author author) {
        authorService.createOrUpdateAuthor(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity deleteAuthorById(@PathVariable long authorId) {
        if (authorService.isAuthorPresent(authorId)) {
            authorService.deleteAuthorById(authorId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Author> findAuthorByName(@PathVariable String name) {
        Author responseAuthor = authorService.findAuthorByName(name);
        if (responseAuthor != null) {
            return new ResponseEntity<>(responseAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
