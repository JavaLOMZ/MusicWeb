package junior.academy.dao;

import junior.academy.domain.Author;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class AuthorDaoTest extends EntityDaoTest {

    @Autowired
    AuthorDao authorDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Author.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    @Test
    public void getAllAuthors() {
        assertEquals(authorDao.getAllAuthors().size(), 1);
    }

    @Test
    public void getAuthorById() {
        assertNotNull(authorDao.getAuthorById(1));
        assertTrue(authorDao.getAuthorById(1).get().getName().equals("Bon Jovi"));
    }

    @Test
    public void createAuthor() {
        Author author = getAuthor();
        authorDao.createOrUpdateAuthor(author);
        assertEquals(authorDao.getAllAuthors().size(), 2);
    }

    @Test
    public void updateAuthor() {
        Author authorTest = authorDao.getAuthorById(1).get();
        authorTest.setName("2pac");
        authorDao.createOrUpdateAuthor(authorTest);
        assertTrue(authorDao.getAuthorById(1).get().getName().equals("2pac"));
    }

    @Test
    public void deleteAuthor() {
        authorDao.deleteAuthorById(1);
        assertEquals(authorDao.getAllAuthors().size(), 0);
    }

    private Author getAuthor() {
        Author author = new Author();
        author.setName("Snoop Dogg");
        author.setCountryOfOrigin("Compton");
        author.setYearOfBirth(1970);
        return author;
    }
}
