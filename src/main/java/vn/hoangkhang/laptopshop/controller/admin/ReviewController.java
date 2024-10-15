package vn.hoangkhang.laptopshop.controller.admin;

import java.util.List;
import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.ReviewMongo;
import vn.hoangkhang.laptopshop.domain.dto.ReviewCriteriaDTO;
import vn.hoangkhang.laptopshop.service.ProductMongoService;

@Controller
public class ReviewController {
    private final ProductMongoService productMongoService;

    public ReviewController(ProductMongoService productMongoService) {
        this.productMongoService = productMongoService;
    }

    @GetMapping("/admin/review")
    public String getReviewPageByProduct(Model model, ReviewCriteriaDTO reviewCriteriaDTO,
            HttpServletRequest request) throws ParseException {
        int page = 1;
        try {
            if (reviewCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(reviewCriteriaDTO.getPage().get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
        }

        String productId = reviewCriteriaDTO.getProductId() != null &&
                reviewCriteriaDTO.getProductId().isPresent()
                        ? reviewCriteriaDTO.getProductId().get()
                        : "";
        ProductMongo product = this.productMongoService.getProductById(productId);
        List<ReviewMongo> reviews = null;
        int totalPages = 0;

        String rating = "";
        String fromDate = "";
        String toDate = "";
        String sort = "";

        if (product != null) {
            reviews = product.getReviews();

            int size = 1;
            int skip = (page - 1) * size;

            reviews = this.productMongoService.filterWithReviews(reviews, reviewCriteriaDTO);

            totalPages = (int) Math.ceil((double) reviews.size() / size);

            reviews = reviews.stream().skip(skip).limit(size).toList();

            rating = reviewCriteriaDTO.getRating().get();
            fromDate = reviewCriteriaDTO.getFromDate().get();
            toDate = reviewCriteriaDTO.getToDate().get();
            sort = reviewCriteriaDTO.getSort().get();
        }

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            // remove page
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("productList", this.productMongoService.getAllProductsWithoutPagination());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("queryString", qs);

        model.addAttribute("rating", rating);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("sort", sort);

        return "admin/review/show";
    }

    @GetMapping("/admin/review/{id}")
    public String getReviewDetailPage(Model model, @PathVariable("id") String reviewId,
            ReviewCriteriaDTO reviewCriteriaDTO) {

        ProductMongo product = this.productMongoService.getProductById(reviewCriteriaDTO.getProductId().get());
        ReviewMongo review = this.productMongoService.getReviewById(reviewId);

        int page = Integer.parseInt(reviewCriteriaDTO.getPage().get());
        String productId = reviewCriteriaDTO.getProductId().get();
        String rating = reviewCriteriaDTO.getRating().get();
        String fromDate = reviewCriteriaDTO.getFromDate().get();
        String toDate = reviewCriteriaDTO.getToDate().get();
        String sort = reviewCriteriaDTO.getSort().get();

        model.addAttribute("product", product);
        model.addAttribute("review", review);
        model.addAttribute("productId", productId);
        model.addAttribute("currentPage", page);
        model.addAttribute("rating", rating);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("sort", sort);

        return "admin/review/detail";
    }

    @GetMapping("/admin/review/del/{id}")
    public String getReviewDeletePage(Model model, @PathVariable("id") String reviewId,
            ReviewCriteriaDTO reviewCriteriaDTO) {

        ReviewMongo newReview = new ReviewMongo();
        newReview.setId(reviewId);

        int page = Integer.parseInt(reviewCriteriaDTO.getPage().get());
        String productId = reviewCriteriaDTO.getProductId().get();
        String rating = reviewCriteriaDTO.getRating().get();
        String fromDate = reviewCriteriaDTO.getFromDate().get();
        String toDate = reviewCriteriaDTO.getToDate().get();
        String sort = reviewCriteriaDTO.getSort().get();

        model.addAttribute("newReview", newReview);
        model.addAttribute("productId", productId);
        model.addAttribute("currentPage", page);
        model.addAttribute("rating", rating);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("sort", sort);

        return "admin/review/delete";
    }

    @PostMapping("/admin/review-delete")
    public String handleDeleteProductReview(@ModelAttribute("newReview") ReviewMongo review,
            ReviewCriteriaDTO reviewCriteriaDTO, @RequestParam("ratingStar") String ratingStar) {

        int page = Integer.parseInt(reviewCriteriaDTO.getPage().get());
        String productId = reviewCriteriaDTO.getProductId().get();
        String rating = ratingStar;
        String fromDate = reviewCriteriaDTO.getFromDate().get();
        String toDate = reviewCriteriaDTO.getToDate().get();
        String sort = reviewCriteriaDTO.getSort().get();

        this.productMongoService.handleDeleteReview(review.getId(), productId);

        return "redirect:/admin/review?page=" + page + "&productId=" + productId
                + "&rating=" + rating + "&fromDate=" + fromDate
                + "&toDate=" + toDate + "&sort=" + sort;
    }
}
