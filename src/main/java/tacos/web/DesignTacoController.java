package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class   DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model) {
        log.info("This is DesignTacoController::showDesignForm");
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "面粉玉米饼", Type.WRAP),
                new Ingredient("COTO", "玉米饼", Type.WRAP),
                new Ingredient("GRBF", "碎牛肉", Type.PROTEIN),
                new Ingredient("CARN", "猪肉丝", Type.PROTEIN),
                new Ingredient("TMTO", "番茄丁", Type.VEGGIES),
                new Ingredient("LETC", "生菜", Type.VEGGIES),
                new Ingredient("CHED", "切达乳酪", Type.CHEESE),
                new Ingredient("JACK", "蒙特雷杰克乳酪", Type.CHEESE),
                new Ingredient("SLSA", "沙拉", Type.SAUCE),
                new Ingredient("SRCR", "酸奶油", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();

        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), 
			filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        log.info(model.toString());
        return "design";
    }

    /**
     *
     * @param design 带校验功能的实体类
     * @param errors 校验的错误信息
     * @return 处理正确，则返回order视图，否则返回design视图
     */
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Processing design has errors" + errors);
            return "design";
        }
        log.info("Processing design:" + design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
