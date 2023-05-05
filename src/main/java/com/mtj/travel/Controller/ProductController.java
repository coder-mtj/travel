package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.BrandService;
import com.mtj.travel.Service.ProductService;
import com.mtj.travel.entity.Brand;
import com.mtj.travel.entity.Product;
import com.mtj.travel.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    // 分页查询商品列表
    @GetMapping
    public Result listProducts(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Page<Product> productPage = productService.page(new Page<>(page, size));
        return new Result("200", productPage, "success");
    }

    // 根据 ID 查询单个商品信息
    @GetMapping("/{id}")
    public Result getProductById(@PathVariable String id) {
        Product product = productService.getById(id);
        return new Result("200", product, "success");
    }

    // 添加商品信息
    @PostMapping
    public Result addProduct(@RequestBody Product product) {
        boolean isSuccess = productService.save(product);
        if (isSuccess) {
            return new Result("200", product, "add success");
        } else {
            return new Result("500", null, "add failed");
        }
    }

    // 更新商品信息
    @PutMapping("/{id}")
    public Result updateProductById(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        boolean isSuccess = productService.updateById(product);
        if (isSuccess) {
            return new Result("200", product, "update success");
        } else {
            return new Result("500", null, "update failed");
        }
    }

    // 删除商品信息
    @DeleteMapping("/{id}")
    public Result deleteProductById(@PathVariable String id) {
        boolean isSuccess = productService.removeById(id);
        if (isSuccess) {
            return new Result("200", id, "delete success");
        } else {
            return new Result("500", null, "delete failed");
        }
    }

    // 根据名称和类别模糊查询商品信息
    @GetMapping("/search")
    public Result searchProductsByKeywordAndCategory(@RequestParam String keyword,
                                                     @RequestParam(required = false) String category,
                                                     @RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        if (category != null) {
            queryWrapper.eq("category", category);
        }
        Page<Product> productPage = productService.page(new Page<>(page, size), queryWrapper);
        return new Result("200", productPage,"success");
    }

    // 获取商品品牌列表
    @GetMapping("/brands")
    public Result listProductBrands() {
        List<Brand> list = brandService.list();
        return new Result("200", list, "success");
    }

    // 根据品牌查询商品列表
    @GetMapping("/brand/{brand}")
    public Result searchProductsByBrand(@PathVariable String brand) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand", brand);
        List<Product> productList = productService.list(queryWrapper);
        return new Result("200", productList, "success");
    }

    // 查询某个类别的商品数量
    @GetMapping("/count")
    public Result countProductsByCategory(@RequestParam String category) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", category);
        Long count = productService.count(queryWrapper);
        return new Result("200", count, "success");
    }
}
