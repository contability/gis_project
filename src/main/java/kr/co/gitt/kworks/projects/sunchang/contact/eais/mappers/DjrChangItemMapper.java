package kr.co.gitt.kworks.projects.sunchang.contact.eais.mappers;

import java.util.List;

import kr.co.gitt.kworks.contact.eais.model.DjrChangItem;

public interface DjrChangItemMapper {
	public List<DjrChangItem> djrChangItemList(int bldrgstPk);
}
