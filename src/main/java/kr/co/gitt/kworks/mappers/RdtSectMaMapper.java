package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.RdtSectMa;

public interface RdtSectMaMapper {
	
	public RdtSectMa selectOne(Long ftrIdn);
	
	public List<RdtSectMa> list(RdtSectMa rdtSectMa);
	
	public Integer insert(RdtSectMa rdtSectMa);
	
	public Integer delete(RdtSectMa rdtSectMa);
	
	public Integer update(RdtSectMa rdtSectMa);
}