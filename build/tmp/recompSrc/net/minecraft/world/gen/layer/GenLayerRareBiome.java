package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRareBiome extends GenLayer
{
    private static final String __OBFID = "CL_00000562";

    public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_)
    {
        super(p_i45478_1_);
        this.parent = p_i45478_3_;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
    {
        int[] aint = this.parent.getInts(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
        int[] aint1 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);

        for (int i1 = 0; i1 < p_75904_4_; ++i1)
        {
            for (int j1 = 0; j1 < p_75904_3_; ++j1)
            {
                this.initChunkSeed((long)(j1 + p_75904_1_), (long)(i1 + p_75904_2_));
                int k1 = aint[j1 + 1 + (i1 + 1) * (p_75904_3_ + 2)];

                if (this.nextInt(57) == 0)
                {
                    if (k1 == BiomeGenBase.plains.biomeID)
                    {
                        aint1[j1 + i1 * p_75904_3_] = BiomeGenBase.plains.biomeID + 128;
                    }
                    else
                    {
                        aint1[j1 + i1 * p_75904_3_] = k1;
                    }
                }
                else
                {
                    aint1[j1 + i1 * p_75904_3_] = k1;
                }
            }
        }

        return aint1;
    }
}