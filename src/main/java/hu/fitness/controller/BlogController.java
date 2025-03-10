package hu.fitness.controller;

import hu.fitness.dto.*;
import hu.fitness.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/blog")
@Tag(name = "Blog Functions", description = "Manage Blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @GetMapping("/")
    @Operation(summary = "List all Blogs")
    public List<BlogList> getAllBlogs(){
        return blogService.listBlogs();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get Blog by id")
    public BlogRead getBlogById(@PathVariable int id){
        return blogService.readBlogById(id);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Create Blog")
    public BlogRead createBlog(@Valid @RequestBody BlogSave blogSave){
        return blogService.createBlog(blogSave);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Blog by id")
    public BlogRead deleteBlog(@PathVariable int id){
        return blogService.deleteBlog(id);
    }

    @CrossOrigin
    @PutMapping("{id}")
    @Operation(summary = "Update Blog by id")
    public BlogRead updateBlog(@PathVariable int id, @Valid @RequestBody BlogUpdate blogUpdate){
        return blogService.updateBlog(id, blogUpdate);
    }

    @CrossOrigin
    @PostMapping(value="/upload-picture/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Blog's picture")
    public PictureRead uploadPicture(@RequestParam("file") MultipartFile file, @PathVariable Integer id){
        return blogService.store(file, id);
    }
}
