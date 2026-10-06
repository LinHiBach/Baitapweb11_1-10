package vn.hcmute.test;

import java.util.List;
import vn.hcmute.dao.ICategoryDao_24110165;
import vn.hcmute.dao.IVideoDao_24110165;
import vn.hcmute.dao.impl.CategoryDaoImpl_24110165;
import vn.hcmute.dao.impl.VideoDaoImpl_24110165;
import vn.hcmute.entity.Category_24110165;
import vn.hcmute.entity.Video_24110165;

public class TestJpa {
    public static void main(String[] args) {
        try {
            System.out.println("--- TESTING CATEGORY DAO ---");
            ICategoryDao_24110165 catDao = new CategoryDaoImpl_24110165();
            List<Category_24110165> categories = catDao.findAll();
            System.out.println("Categories found: " + categories.size());
            for (Category_24110165 c : categories) {
                System.out.println("Category: id=" + c.getCategoryId() + ", name=" + c.getCategoryname() + ", code=" + c.getCategorycode());
            }

            System.out.println("\n--- TESTING VIDEO DAO ---");
            IVideoDao_24110165 videoDao = new VideoDaoImpl_24110165();
            List<Video_24110165> videos = videoDao.findAll();
            System.out.println("Videos found: " + videos.size());
            for (Video_24110165 v : videos) {
                System.out.println("Video: id=" + v.getVideoId() + ", title=" + v.getTitle() + 
                    ", category=" + (v.getCategory() != null ? v.getCategory().getCategoryname() : "NULL"));
            }
            System.out.println("\n--- TEST SUCCESSFUL ---");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
