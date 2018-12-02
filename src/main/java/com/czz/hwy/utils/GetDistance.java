package com.czz.hwy.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDistance {

	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI= 6.28318530712; // 2*PI
	static double DEF_PI180= 0.01745329252; // PI/180.0
	static double DEF_R =6370693.5; // radius of earth
	public static double getShortDistance(double lon1, double lat1, double lon2, double lat2)
	{
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
			// 角度转换为弧度
			ew1 = lon1 * DEF_PI180;
			ns1 = lat1 * DEF_PI180;
			ew2 = lon2 * DEF_PI180;
			ns2 = lat2 * DEF_PI180;
			// 经度差
			dew = ew1 - ew2;
			// 若跨东经和西经180 度，进行调整
			if (dew > DEF_PI)
			dew = DEF_2PI - dew;
			else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
			dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
			dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
			// 勾股定理求斜边长
			distance = Math.sqrt(dx * dx + dy * dy);
			return distance;
		}
	
		/***
		 * 判断两个点的距离
		 * @param lon1
		 * @param lat1
		 * @param lon2
		 * @param lat2
		 * @return
		 */
		public static double getLongDistance(double lon1, double lat1, double lon2, double lat2)
		{
			double ew1, ns1, ew2, ns2;
			double distance;
			// 角度转换为弧度
			ew1 = lon1 * DEF_PI180;
			ns1 = lat1 * DEF_PI180;
			ew2 = lon2 * DEF_PI180;
			ns2 = lat2 * DEF_PI180;
			// 求大圆劣弧与球心所夹的角(弧度)
			distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
			// 调整到[-1..1]范围内，避免溢出
			if (distance > 1.0)
			     distance = 1.0;
			else if (distance < -1.0)
			      distance = -1.0;
			// 求大圆劣弧长度
			distance = DEF_R * Math.acos(distance);
			return distance;
		}
		
		public static void main(String[] args) {
/*//			double mLat1 = 39; // point1纬度
//			double mLon1 = 116; // point1经度
//			double mLat2 = 39;// point2纬度
//			double mLon2 = 116;// point2经度
//			double distance = getShortDistance(mLon1, mLat1, mLon2, mLat2);
//			System.out.println(distance);
			
//			getPointListByPoint(37.522030, 105.190389 , 100, 360);
			getPointListByPoint(37.522030, 105.190389 , 100, 360);*/
			
			double px = 37.512202;
			double py = 105.200824;
			List<Double> polygonXA = new ArrayList<Double>();
			List<Double> polygonYA = new ArrayList<Double>();
			polygonXA.add(37.530149);
			polygonXA.add(37.511261);
			polygonXA.add(37.511262);
			polygonXA.add(37.530145);
			
			polygonYA.add(105.196692);
			polygonYA.add(105.196699);
			polygonYA.add(105.222725);
			polygonYA.add(105.222722);
			//boolean isflag = isPointInPolygon(px,py,polygonXA,polygonYA);
            //System.out.println(isflag);
		}
		
		/**
		 * 判断点是否在由多个经纬度围成的多边形的范围内
		 * @param px 点的经度
		 * @param py 点的纬度
		 * @param polygonXA  多边形的经度集合
		 * @param polygonYA  多边形的纬度集合
		 * @return
		 */
/*		public static  boolean isPointInPolygon ( double px , double py , List<Double> polygonXA , List<Double> polygonYA )  
	        {  
	            boolean isInside = false;  
	            double ESP = 1e-9;  
	            int count = 0;  
	            double linePoint1x;  
	            double linePoint1y;  
	            double linePoint2x = 180;  
	            double linePoint2y;  
	      
	            linePoint1x = px;  
	            linePoint1y = py;  
	            linePoint2y = py;  
	      
	            for (int i = 0; i < polygonXA.size() - 1; i++)  
	            {  
	                double cx1 = polygonXA.get(i);  
	                double cy1 = polygonYA.get(i);  
	                double cx2 = polygonXA.get(i + 1);  
	                double cy2 = polygonYA.get(i + 1);  
	                if ( isPointOnLine(px, py, cx1, cy1, cx2, cy2) )  
	                {  
	                    return true;  
	                }  
	                if ( Math.abs(cy2 - cy1) < ESP )  
	                {  
	                    continue;  
	                }  
	      
	                if ( isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x, linePoint2y) )  
	                {  
	                    if ( cy1 > cy2 )  
	                        count++;  
	                }  
	                else if ( isPointOnLine(cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y) )  
	                {  
	                    if ( cy2 > cy1 )  
	                        count++;  
	                }  
	                else if ( isIntersect(cx1, cy1, cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y) )  
	                {  
	                    count++;  
	                }  
	            }  
	            //System.out.println(count);  
	            if ( count % 2 == 1 )  
	            {  
	                isInside = true;  
	            }  
	      
	            return isInside;  
	        } */ 
	     
        public static double Multiply ( double px0 , double py0 , double px1 , double py1 , double px2 , double py2 )  
        {  
            return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));  
        }  
	      
        public static boolean isPointOnLine ( double px0 , double py0 , double px1 , double py1 , double px2 , double py2 )  
        {  
            boolean flag = false;  
            double ESP = 1e-9;  
            if ( (Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP) && ((px0 - px1) * (px0 - px2) <= 0)  
                    && ((py0 - py1) * (py0 - py2) <= 0) )  
            {  
                flag = true;  
            }  
            return flag;  
        }  
	      
        public static boolean isIntersect ( double px1 , double py1 , double px2 , double py2 , double px3 , double py3 , double px4 ,  
                double py4 )  
        {  
            boolean flag = false;  
            double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);  
            if ( d != 0 )  
            {  
                double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3)) / d;  
                double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1)) / d;  
                if ( (r >= 0) && (r <= 1) && (s >= 0) && (s <= 1) )  
                {  
                    flag = true;  
                }  
            }  
            return flag;  
        }
        
        /**
         * 判断点是否在由多个经纬度围成的多边形的范围内
         * @param px 点的经度
         * @param py 点的纬度
         * @param polygonXA  多边形的经度集合
         * @param polygonYA  多边形的纬度集合
         * @return
         */
        public static  boolean isPointInPolygon (Point2D.Double point, List<Point2D.Double> pts )  
            {  
            int N = pts.size();
            boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
            int intersectCount = 0;//cross points count of x 
            double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
            Point2D.Double p1, p2;//neighbour bound vertices
            Point2D.Double p = point; //当前点
            
            p1 = pts.get(0);//left vertex        
            for(int i = 1; i <= N; ++i){//check all rays            
                if(p.equals(p1)){
                    return boundOrVertex;//p is an vertex
                }
                
                p2 = pts.get(i % N);//right vertex            
                if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests                
                    p1 = p2; 
                    continue;//next ray left point
                }
                
                if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
                    if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray                    
                        if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
                            return boundOrVertex;
                        }
                        
                        if(p1.y == p2.y){//ray is vertical                        
                            if(p1.y == p.y){//overlies on a vertical ray
                                return boundOrVertex;
                            }else{//before ray
                                ++intersectCount;
                            } 
                        }else{//cross point on the left side                        
                            double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y                        
                            if(Math.abs(p.y - xinters) < precision){//overlies on a ray
                                return boundOrVertex;
                            }
                            
                            if(p.y < xinters){//before ray
                                ++intersectCount;
                            } 
                        }
                    }
                }else{//special case when ray is crossing through the vertex                
                    if(p.x == p2.x && p.y <= p2.y){//p crossing over p2                    
                        Point2D.Double p3 = pts.get((i+1) % N); //next vertex                    
                        if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
                            ++intersectCount;
                        }else{
                            intersectCount += 2;
                        }
                    }
                }            
                p1 = p2;//next ray left point
            }
            
            if(intersectCount % 2 == 0){//偶数在多边形外
                return false;
            } else { //奇数在多边形内
                return true;
            }
            }          
        
        /**
         * 以一个点的经纬度为圆点，radius变量值为半径，取圆上num个点的坐标
         * @param lat 圆心的纬度
         * @param lng 圆心的经度
         * @param radius 圆的半径
         * @param num    圆上点的个数
         * @return
         */
//		public static List<Map<String, Double>> getPointListByPoint(
//				double lat, double lng, int radius, int num) {
//			List<Map<String, Double>> list = new ArrayList<Map<String,Double>>();
//			Map<String, Double> map = null;
//			for(int i = 0; i <= num; i++){
//				//角度转换为弧度
//				double radian = DEF_PI180 * i;
//				//算出角度为i的圆上的坐标
//				double x = lat + Math.cos(radian) * radius;
//				double y = lng + Math.sin(radian) * radius;
//				
//				map = new HashMap<String, Double>();
//				map.put("lat", x);
//				map.put("lng", y);
////				System.out.println(map);
//				System.out.println(map.get("lat") + "," + map.get("lng") + ";");
//				list.add(map);
//			}
//			return list;
//		}  

		 /**
         * 知道一点的经纬度、距离,求另一点的经纬度
         * @param lat 点的纬度
         * @param lng 点的经度
         * @param radius 点到另一点的距离：单位为米
         * @param num    要取得另一个点的距离
         * @return
         */
		public static List<Map<String, Double>> getPointListByPoint(
				double lat, double lng, double distance, int num) {
			List<Map<String, Double>> list = new ArrayList<Map<String,Double>>();
			Map<String, Double> map = null;
			for(int i = 0; i < num; i++){
				//地球的周长几乎都是相等的,所以,都可看成,走过111km 就是走过1度.
				//将到另一点的距离换成度数
				double degree = distance/111000;
				
				//角度转换为弧度
				double radian = DEF_PI180 * i;
				
				//计算纬度
				double y = lat + Math.sin(radian) * degree;
				
				//计算经度
				double x = lng + Math.cos(radian) * degree;
				
				map = new HashMap<String, Double>();
				map.put("lat", y);
				map.put("lng", x);
				list.add(map);
			}
			return list;
		}  

}
