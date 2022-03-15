
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 Этот класс наследуется от фигуры и отвечает за рисование линии.
 */

public class MyLine extends MyShape
{
    public int counter = 0;
    public MyLine() {
        super();
    }
    
    public MyLine(int startX, int startY, int endX, int endY, Color color, int width, BufferedImage img, int Radius, int Corner, int Turn,int InternalRadius) {
        super(startX, startY, endX, endY, color, width, img, Radius, Corner, Turn , InternalRadius);
    }

    // Этот код "рисует" все 9 видов отрезков. Наклонные (из начала в конец и из конца в начало каждый), вертикальный и горизонтальный - тоже из начала в конец и из конца в начало, и точку.
    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public void drawBresenhamLine (int xstart, int ystart, int xend, int yend)
    /**
     * xstart, ystart - начало;
     * xend, yend - конец;
     * Можно писать что-нибудь вроде g.fillRect (x, y, 1, 1);
     */
    {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy)
        //определяем наклон отрезка:
        {
            /*
             * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
             * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
             * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
             * по y сдвиг такой отсутствует.
             */
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        }
        else //случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el/2;
        getImage().setRGB(x,y,getColor().getRGB());
        //g.drawLine(x,y,x,y);
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0)
            {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }
            else
            {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }
            getImage().setRGB(x,y,getColor().getRGB());
            //g.drawLine(x,y,x,y);
        }
    }
  
    @Override
    public void draw(Graphics g ) {
        counter = counter + 1;
        int width = getWidth();
        g.setColor(getColor());
        if(width == 1){
            drawBresenhamLine(getStartX(),getStartY(),getEndX(),getEndY());
           // counter = 0;
        }
        else{
            Graphics2D g2 = (Graphics2D) g;
            g2 = (Graphics2D) getImage().getGraphics();
            g2.setColor(getColor());
            g2.setStroke(new BasicStroke(width));
            g2.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
        }
    }
}
