package hu.fitness.service;

import hu.fitness.converter.BlogConverter;
import hu.fitness.domain.Blog;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.*;
import hu.fitness.exception.BlogNotFoundException;
import hu.fitness.exception.FailedSaveException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.BlogRepository;
import hu.fitness.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public List<BlogList> listBlogs() {
        List<BlogList> blogList = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAll();
        blogList = BlogConverter.convertModelsToList(blogs);
        return blogList;
    }

    public BlogRead readBlogById(Integer id){
        if(!blogRepository.existsById(id)){
            throw new BlogNotFoundException();
        }
        Blog blog = blogRepository.getReferenceById(id);
        return BlogConverter.convertModelToRead(blog);
    }

    public BlogRead createBlog(BlogSave blogSave){
        if(!trainerRepository.existsById(blogSave.getTrainerId())){
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(blogSave.getTrainerId());
        Blog blog = BlogConverter.convertSaveToModel(blogSave,trainer);
        Blog savedBlog = blogRepository.save(blog);
        return BlogConverter.convertModelToRead(savedBlog);
    }

    public BlogRead deleteBlog(int id){
        if(!blogRepository.existsById(id)){
            throw new BlogNotFoundException();
        }

        BlogRead blogRead = BlogConverter.convertModelToRead(blogRepository.getReferenceById(id));
        blogRepository.deleteById(id);
        return blogRead;
    }

    public BlogRead updateBlog(int id, BlogUpdate blogUpdate){
        if (!blogRepository.existsById(id)) {
            throw new BlogNotFoundException();
        }

        Blog blog = blogRepository.getReferenceById(id);
        Trainer trainer = blog.getTrainer();
        blog.setBlogType(blogUpdate.getBlogType());
        blog.setTitle(blogUpdate.getTitle());
        blog.setHeaderText(blogUpdate.getHeaderText());
        blog.setMainText(blogUpdate.getMainText());
        blog.setImage(blogUpdate.getImage());
        blog.setTrainer(trainer);
        blogRepository.save(blog);
        return BlogConverter.convertModelToRead(blog);
    }

    @Transactional
    public PictureRead store(MultipartFile file, Integer blogId) {
        String rootFolder = "src/main/resources/static/images/";

        if(!blogRepository.existsById(blogId)){
            throw new BlogNotFoundException();
        }
        Blog blog = blogRepository.getReferenceById(blogId);

        String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/blogImages/";
        File fullPath = new File(rootFolder + dateFolder);
        String fullFolderName = rootFolder + dateFolder;
        if (!fullPath.exists()) {
            if (!fullPath.mkdirs()) {
                fullFolderName = rootFolder;
            }
        }

        String uniqueFileName = createSavingFileName(file);
        Path destinationFilePath = Paths.get(fullFolderName + uniqueFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FailedSaveException();
        }
        blog.setImage("/images/" + dateFolder + uniqueFileName);
        blogRepository.save(blog);
        PictureRead pictureRead = new PictureRead();
        pictureRead.setId(blog.getId());
        pictureRead.setFullPath(blog.getImage());
        return pictureRead;
    }

    private static String createSavingFileName(MultipartFile file) {
        String fileNameUniquePart = '-' + new SimpleDateFormat("HH-mm-ss").format(new Date()) + '-' + (int)(Math.random() * 1000);
        String fileName = file.getOriginalFilename().split("\\.")[0];
        String fileExtension = file.getOriginalFilename().split("\\.")[1];
        return fileName + fileNameUniquePart + '.' + fileExtension;
    }

}
