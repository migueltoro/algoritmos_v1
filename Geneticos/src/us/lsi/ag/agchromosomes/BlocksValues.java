package us.lsi.ag.agchromosomes;


import java.util.List;

import us.lsi.ag.BlocksData;

public class BlocksValues<S> implements ChromosomeValues<List<Integer>, List<Integer>, S> {
	
	public static <S> BlocksValues<S> of(BlocksData<S> data) {
        return new BlocksValues<S>(data);
    }

    private BlocksData<S> data;

    private BlocksValues(BlocksData<S> data) {
        this.data = data;
    }
    
    @Override
    public BlocksData<S> data() {
        return data;
    }

    @Override
    public List<Integer> decodeValues(List<Integer> r) {
		return r;
    }
    
    @Override
    public Integer dimension() {
        return data.size();
    }
    
    
}
