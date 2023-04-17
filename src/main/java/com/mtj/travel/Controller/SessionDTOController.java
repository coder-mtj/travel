package com.mtj.travel.Controller;
import com.mtj.travel.DTO.SessionDTO;
import com.mtj.travel.Service.Impl.SessionDTOServiceImpl;
import com.mtj.travel.Service.SessionDTOService;
import com.mtj.travel.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 场次DTO 控制器
 */
@RestController
@RequestMapping("/Session")
public class SessionDTOController {
    @Autowired
    private SessionDTOServiceImpl sessionDTOService;
    /**
     * 根据景区ID、时间范围查找场次信息（按时间升序排列）
     *
     * @param scenicAreaId 景区ID
     * @param startDate    开始日期（格式：yyyy-MM-dd）
     * @param endDate      结束日期（格式：yyyy-MM-dd）
     * @return 匹配的场次信息列表
     */
    @GetMapping("/listByScenicAreaAndDateRange")
    public Result listByScenicAreaAndDateRange(@RequestParam("scenicAreaId") String scenicAreaId, @RequestParam("startDate") String startDate,
                                               @RequestParam("endDate") String endDate) {
        List<SessionDTO> list = sessionDTOService.listByScenicAreaAndDateRange(scenicAreaId, LocalDate.parse(startDate), LocalDate.parse(endDate));
        return new Result("200", list, "查询成功");
    }

    /**
     * 根据场次ID获取场次信息（包含景区信息）
     *
     * @param id 场次ID
     * @return 对应的场次信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") String id) {
        SessionDTO sessionDTO = sessionDTOService.getById(id);
        return new Result("200", sessionDTO, "查询成功");
    }

    /**
     * 新增场次信息
     *
     * @param sessionDTO 场次信息
     * @return 操作结果
     */
    @PostMapping("/")
    public Result add(@RequestBody SessionDTO sessionDTO) {
        boolean result = sessionDTOService.save(sessionDTO);
        if (result) {
            return new Result("200", null, "新增成功");
        } else {
            return new Result("500", null, "新增失败");
        }
    }

    /**
     * 修改场次信息
     *
     * @param sessionDTO 场次信息
     * @return 操作结果
     */
    @PutMapping("/")
    public Result update(@RequestBody SessionDTO sessionDTO) {
        boolean result = sessionDTOService.updateById(sessionDTO);
        if (result) {
            return new Result("200", null, "更新成功");
        } else {
            return new Result("500", null, "更新失败");
        }
    }

    /**
     * 根据场次ID删除场次信息
     *
     * @param id 场次ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        boolean result = sessionDTOService.removeById(id);
        if (result) {
            return new Result("200", null, "删除成功");
        } else {
            return new Result("500", null, "删除失败");
        }
    }
}
