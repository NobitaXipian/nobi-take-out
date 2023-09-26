package com.xipian.nobi.controller.user;

import com.xipian.nobi.constant.StatusConstant;
import com.xipian.nobi.entity.Dish;
import com.xipian.nobi.result.Result;
import com.xipian.nobi.service.DishService;
import com.xipian.nobi.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        //查Redis是否存在菜品数据
        String key = "dish_"+categoryId;
        List<DishVO> listCache = (List<DishVO>) redisTemplate.opsForValue().get(key);
        //存在则直接返回，不存在则查询数据库，并将数据放入Redis
        if (listCache != null && listCache.size() > 0){
            return Result.success(listCache);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        List<DishVO> list = dishService.listWithFlavor(dish);

        //将数据放入Redis
        redisTemplate.opsForValue().set(key,list);

        return Result.success(list);
    }

}
