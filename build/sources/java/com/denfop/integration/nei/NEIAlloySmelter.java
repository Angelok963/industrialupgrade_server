package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.IDoubleMachineRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIAlloySmelter;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEIAlloySmelter extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIAlloySmelter.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.Alloymachine.name");
    }

    public String getRecipeId() {
        return "iu.alloysmelter";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIAlloySmelter.png";
    }

    public String getOverlayIdentifier() {
        return "alloysmelter";
    }

    public Map<IDoubleMachineRecipeManager.Input, RecipeOutput> getRecipeList() {
        return Recipes.Alloysmelter.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 16, 140, 65);
    }

    public void drawExtras(int i) {
        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(74, 19, 176, 14, 25, 16, f, 0);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(52, 20, 176, 0, 14, 14, f, 3);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 19, 25, 14),
                getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new AlloySmelterRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new AlloySmelterRecipe(entry.getKey().container,
                                    entry.getKey().fill, entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if (entry.getKey().container.matches(ingredient)
                    || entry.getKey().fill.matches(ingredient))
                this.arecipes.add(new AlloySmelterRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getValue()));
        }
    }

    public class AlloySmelterRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();

        public AlloySmelterRecipe(IRecipeInput container, IRecipeInput fill, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            List<ItemStack> fillItems = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));
            for (ItemStack item : fill.getInputs())
                fillItems.add(StackUtil.copyWithSize(item, fill.getAmount()));
            this.ingredients.add(new PositionedStack(containerItems, 33, 1));
            this.ingredients.add(new PositionedStack(fillItems, 69, 1));
            this.output = new PositionedStack(output1.items.get(0), 111, 18);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIAlloySmelter.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
