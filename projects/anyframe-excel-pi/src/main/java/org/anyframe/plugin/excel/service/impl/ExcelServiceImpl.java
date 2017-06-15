/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.anyframe.plugin.excel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.plugin.excel.service.ExcelService;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Service;

@Service("excelService")
public class ExcelServiceImpl implements ExcelService{
	
	@Inject
	@Named("excelDao")
	private ExcelDao excelDao;
	
	public List<Map> download(Map map) throws Exception {
		String queryId = (String) map.get("queryId");
		
		List resultList = new ArrayList();
		if ( map.get("pageIndex") != null ){
			resultList = excelDao.getPagingList(queryId, (Integer)map.get("pageIndex") , map);
		}else{
			resultList = excelDao.getList(queryId, map);
		}
		return resultList;
	}

	public int upload(Map infoMap, List<ListOrderedMap> insertList) throws Exception {
		String queryId = (String) infoMap.get("queryId");
		
		int returnValue = 0;
		for ( int i = 0 ; i < insertList.size() ; i ++ ){
			if ( excelDao.create(queryId, insertList.get(i)) != -1 ){
				returnValue = returnValue + 1;
			}
		}
		return returnValue;
	}

}
